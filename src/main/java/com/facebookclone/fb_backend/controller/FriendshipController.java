package com.facebookclone.fb_backend.controller;

import com.facebookclone.fb_backend.entity.Friendship;
import com.facebookclone.fb_backend.entity.User;
import com.facebookclone.fb_backend.service.FriendshipService;
import com.facebookclone.fb_backend.service.UserService;
import com.facebookclone.fb_backend.entity.Friendship;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/friendships")
public class FriendshipController {
    private final FriendshipService friendshipService;
    private final UserService userService;

    public FriendshipController(FriendshipService friendshipService, UserService userService) {
        this.friendshipService = friendshipService;
        this.userService = userService;
    }

    @PostMapping("/add-friend")
    public ResponseEntity<?> createFriendship(@RequestParam("requesterId") Long requesterId,
                                                        @RequestParam("receiverId") Long receiverId,
                                                        @RequestParam("status") String status) {
        User requester = userService.findById(requesterId);
        User receiver = userService.findById(receiverId);
        Friendship friendship = new Friendship();
        friendship.setRequester(requester);
        friendship.setReceiver(receiver);
        friendship.setCreatedAt(LocalDateTime.now());
        
        try {
            friendship.setStatus(Friendship.Status.valueOf(status));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid status value: " + status + ". Allowed values are: pending, accepted, declined.");
        }
        return ResponseEntity.ok(friendshipService.createFriendship(friendship));
    }

    @DeleteMapping("/deleteFriend/{id}")
    public ResponseEntity<?> deleteFriendship(@PathVariable Long id) {
        friendshipService.deleteFriendship(id);
        return ResponseEntity.noContent().build();
    }
    @PostMapping("/request")
    public ResponseEntity<Friendship> sendFriendRequest(
            @RequestParam Long requesterId,
            @RequestParam Long receiverId) {
        User requester = userService.findById(requesterId);
        User receiver = userService.findById(receiverId);
        if (requester == null || receiver == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        Friendship friendship = new Friendship(requester, receiver, Friendship.Status.pending);
        Friendship createdFriendship = friendshipService.createFriendship(friendship);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdFriendship);
    }

    @GetMapping("/received/{receiverId}")
    public ResponseEntity<List<Friendship>> getFriendRequestsReceived(@PathVariable Long receiverId) {
        return ResponseEntity.ok(friendshipService.getFriendRequestsReceived(receiverId));
    }

    @GetMapping("/friends/{userId}")
    public ResponseEntity<List<Friendship>> getFriends(@PathVariable Long userId) {
        return ResponseEntity.ok(friendshipService.getFriends(userId));
    }

    // Hàm này lấy người dùng được gửi lời mời kết bạn và đã chấp nhận
    @GetMapping("/userFriends/{userId}")
    public ResponseEntity<List<Friendship>> getFriendsAcceptedReceive(@PathVariable Long userId) {
        if (userId == null || userId <= 0) {
            return ResponseEntity.badRequest().build(); // Trả về 400 Bad Request nếu userId không hợp lệ
        }
        List<Friendship> friendsAsRequester = friendshipService.getAcceptedFriends(userId);
        List<Friendship> friendsAsReceiver = friendshipService.getFriends(userId);
        List<Friendship> allUserFriends = new ArrayList<>();

        if(friendsAsRequester != null) {
            allUserFriends.addAll(friendsAsRequester);
        }
        if(friendsAsReceiver != null) {
            allUserFriends.addAll(friendsAsReceiver);
        }

        return ResponseEntity.ok(allUserFriends);
    }
    @PostMapping("/accept/{friendshipId}")
    public ResponseEntity<Friendship> acceptFriendRequest(@PathVariable Long friendshipId) {
        Friendship updatedFriendship = friendshipService.acceptFriendRequest(friendshipId);
        return ResponseEntity.ok(updatedFriendship);
    }
}