package com.facebookclone.fb_backend.service;

import com.facebookclone.fb_backend.entity.Friendship;
import com.facebookclone.fb_backend.repository.FriendshipRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class FriendshipService {
    private final FriendshipRepository friendshipRepository;

    public FriendshipService(FriendshipRepository friendshipRepository) {
        this.friendshipRepository = friendshipRepository;
    }

    public Friendship createFriendship(Friendship friendship) {
        return friendshipRepository.save(friendship);
    }

    public void deleteFriendship(Long id) {
        friendshipRepository.deleteById(id);
    }

    public List<Friendship> getFriendRequestsReceived(Long receiverId) {
        List<Friendship> requests = friendshipRepository.findByReceiverIdAndStatus(receiverId, Friendship.Status.pending);
        // Tính mutual friends cho mỗi request
        for (Friendship request : requests) {
            Long requesterId = request.getRequester().getId();
            int mutualFriendsCount = calculateMutualFriends(receiverId, requesterId);
            request.setMutualFriends(mutualFriendsCount);
        }
        return requests;
    }

    public List<Friendship> getFriends(Long userId) {
        List<Friendship> friendsAsRequester = friendshipRepository.findByRequesterIdAndStatus(userId, Friendship.Status.accepted);
        List<Friendship> friendsAsReceiver = friendshipRepository.findByReceiverIdAndStatus(userId, Friendship.Status.accepted);
        friendsAsRequester.addAll(friendsAsReceiver);
        return friendsAsRequester;
    }

    public List<Friendship> getAcceptedFriends(Long userId) {
        return friendshipRepository.findByReceiverIdAndStatus(userId, Friendship.Status.accepted);
    }

    @Transactional
    public Friendship acceptFriendRequest(Long friendshipId) {
        Friendship friendship = friendshipRepository.findById(friendshipId)
                .orElseThrow(() -> new EntityNotFoundException("Không tìm thấy lời mời kết bạn với ID: " + friendshipId));
        if (friendship.getStatus() != Friendship.Status.pending) {
            throw new IllegalStateException("Lời mời kết bạn không ở trạng thái pending");
        }
        friendship.setStatus(Friendship.Status.accepted);
        return friendshipRepository.save(friendship);
    }

    private int calculateMutualFriends(Long userId1, Long userId2) {
        List<Friendship> friendsOfUser1 = getFriends(userId1);
        List<Friendship> friendsOfUser2 = getFriends(userId2);

        Set<Long> friendIdsOfUser1 = friendsOfUser1.stream()
                .map(friendship -> {
                    if (friendship.getRequester().getId().equals(userId1)) {
                        return friendship.getReceiver().getId();
                    } else {
                        return friendship.getRequester().getId();
                    }
                })
                .collect(Collectors.toSet());

        Set<Long> friendIdsOfUser2 = friendsOfUser2.stream()
                .map(friendship -> {
                    if (friendship.getRequester().getId().equals(userId2)) {
                        return friendship.getReceiver().getId();
                    } else {
                        return friendship.getRequester().getId();
                    }
                })
                .collect(Collectors.toSet());

        friendIdsOfUser1.retainAll(friendIdsOfUser2);
        return friendIdsOfUser1.size();
    }
}