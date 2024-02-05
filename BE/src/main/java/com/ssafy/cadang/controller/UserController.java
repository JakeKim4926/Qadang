package com.ssafy.cadang.controller;

import com.ssafy.cadang.domain.User;
import com.ssafy.cadang.dto.KakaoToken;
import com.ssafy.cadang.dto.MaxRecord;
import com.ssafy.cadang.dto.UserAmount;
import com.ssafy.cadang.dto.UserInfo;
import com.ssafy.cadang.service.KakaoService;
import com.ssafy.cadang.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@CrossOrigin
@RestController
@RequiredArgsConstructor
public class UserController {


    private final KakaoService kakaoService;
    private final UserService userService;

//    //test로 직접 인가 코드 받기
//    @GetMapping("/kakao-login")
//    public String KakaoCode(@RequestParam("code") String code) {
//        return "카카오 로그인 인증완료, code: "  + code;
//    }

    //     프론트에서 인가코드 받기
    @GetMapping("/kakao-login")
    public ResponseEntity<?> KakaoLogin(@RequestParam("code") String code) {
//         엑세스 토큰 받기
        KakaoToken oauthToken = kakaoService.getAccessToken(code);

        System.out.println("토큰 : " + oauthToken);

        // 유저가 없으면 회원가입 + 사용자 정보 요청
        User addUser = kakaoService.addUser(oauthToken);
        System.out.println("사용자 정보 요청 완료");

        // 카카오에서 발급받은 access token을 이용해서 jwt 토큰 발급받기
        String jwtAccessToken = kakaoService.getJwtAccess(addUser);
        String jwtRefreshToken = kakaoService.getJwtRefresh(addUser);
        System.out.println("jwt " + jwtAccessToken + " / " + jwtRefreshToken);

        // 헤더에 jwt access token 담기
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + jwtAccessToken);

        return ResponseEntity.ok().headers(headers).body("success");
    }

//    // 직접 인가 코드 받기 ( test )
//    @GetMapping("/kakao-login")
//    public String KakaoLogin(@RequestParam("code") String code) {
////    public ResponseEntity KakaoLogin(@RequestParam("code") String code) {
//
//        // 엑세스 토큰 받기
//        KakaoToken oauthToken = kakaoService.getAccessToken(code);
//
//        System.out.println("토큰 : " + oauthToken);
//        {
//
//            // 유저가 없으면 회원가입 + 사용자 정보 요청
//            User addUser = kakaoService.addUser(oauthToken);
//            System.out.println("사용자 정보 요청 완료");
//
//            // 카카오에서 발급받은 access token을 이용해서 jwt 토큰 발급받기
//            String jwtAccessToken = kakaoService.getJwtAccess(addUser);
//            String jwtRefreshToken = kakaoService.getJwtRefresh(addUser);
//            System.out.println("jwt " + jwtAccessToken + " / " + jwtRefreshToken);
//
//            // 헤더에 jwt access token 담기
//            HttpHeaders headers = new HttpHeaders();
//            headers.add("Authorization", "Bearer " + jwtAccessToken);
//
//
////            return ResponseEntity.ok().headers(headers).body("success");
//            return "success";
//        }
//    }


    // 회원정보조회
    @GetMapping("/api/user")
    public ResponseEntity<UserInfo> userInfo(@RequestHeader("Authorization") String token) {

        // 헤더에서 jwt access 토큰 추출
        String jwtAccess = kakaoService.getJwtToken(token);
        System.out.println("jwtAccess: " + jwtAccess);

        // jwt 토큰이 유효한지 검증
        boolean isVaildation = kakaoService.vaildation(jwtAccess);
        boolean isVaild = kakaoService.validToken(jwtAccess);
        // jwt를 이용해 user 가져오기
        User user = kakaoService.getUser(jwtAccess);
        System.out.println("User: " + user);


        if (isVaildation == false || isVaild == false) { // 유효하지 않다면

            boolean haveRefresh = kakaoService.refreshcheck(jwtAccess);

            if (haveRefresh == false) { // 리프레시가 없다면

                // jwt 토큰 갱신
                String access = kakaoService.getJwtAccess(user);
                String refresh = kakaoService.getJwtRefresh(user);

                // 헤더에 jwt access token 담기
                HttpHeaders header = new HttpHeaders();
                header.add("Authorization", "Bearer " + access);


                return ResponseEntity.ok().headers(header).body(UserInfo.builder()
                        .userName(user.getUserName())
                        .userBirth(user.getUserBirth())
                        .userHeight(user.getUserHeight())
                        .userWeight(user.getUserWeight())
                        .userGender(user.getUserGender())
                        .userGender(user.getUserHealth())
                        .build());
            }
        }
        return new ResponseEntity<>(UserInfo.builder()
                .userName(user.getUserName())
                .userBirth(user.getUserBirth())
                .userHeight(user.getUserHeight())
                .userWeight(user.getUserWeight())
                .userGender(user.getUserGender())
                .userGender(user.getUserHealth())
                .build(), HttpStatus.OK);
    }

    // 회원정보수정
    @PutMapping("/api/user")
    public ResponseEntity<HttpStatus> adduserInfo(@RequestHeader("Authorization") String token, @RequestBody UserInfo userInfo) {

        String jwt = kakaoService.getJwtToken(token);
        User user = kakaoService.getUser(jwt);

        user.setUserBirth(userInfo.userBirth);
        user.setUserHeight(userInfo.userHeight);
        user.setUserWeight(userInfo.userWeight);
        user.setUserGender(userInfo.userGender);
        user.setUserHealth(userInfo.userHealth);
        userService.update(user);

        userService.detailInfo(user);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    // 회원 권장량 조회
    @GetMapping("/api/user/amount")
    public ResponseEntity<UserAmount> userAmount(@RequestHeader("Authorization") String token) {

        String jwt = kakaoService.getJwtToken(token);
        User user = kakaoService.getUser(jwt);

        return new ResponseEntity<UserAmount>(userService.userAmount(user), HttpStatus.OK);
    }

    //회원 당, 카페인 최고 섭취량 및 날짜 조회
    @GetMapping("/api/user/max")
    public ResponseEntity<MaxRecord> userMax(@RequestHeader("Authorization") String token) {

        String jwt = kakaoService.getJwtToken(token);
        User user = kakaoService.getUser(jwt);

        return new ResponseEntity<>(userService.userMax(user), HttpStatus.OK);
    }

    // 회원탈퇴
    @DeleteMapping("/api/user")
    public ResponseEntity<Void> deleteUser(@RequestHeader("Authorization") String token) {
        String jwt = kakaoService.getJwtToken(token);
        User user = kakaoService.getUser(jwt);

        userService.delete(user.getUserId());

        return new ResponseEntity<>(HttpStatus.OK);
    }

    // 로그아웃

}