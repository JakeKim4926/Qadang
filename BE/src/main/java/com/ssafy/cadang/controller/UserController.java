package com.ssafy.cadang.controller;

import com.ssafy.cadang.domain.User;
import com.ssafy.cadang.dto.KakaoToken;
import com.ssafy.cadang.dto.MaxRecord;
import com.ssafy.cadang.dto.UserAmount;
import com.ssafy.cadang.dto.UserInfo;
import com.ssafy.cadang.jwt.JwtLogin;
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
@RequestMapping("/api")
public class UserController {


    private final KakaoService kakaoService;
    private final UserService userService;

////    test로 직접 인가 코드 받기
//    @GetMapping("/kakao-login")
//    public String KakaoCode(@RequestParam("code") String code) {
//        return "카카오 로그인 인증완료, code: " + code;
//    }

    // 프론트에서 인가코드 받아서 로그인 하기 ( 로그아웃한 회원, 회원가입 )
    @GetMapping("/kakao-login")
    public ResponseEntity<JwtLogin> KakaoLogin(@RequestParam("code") String code) {

        System.out.println("[로그인] 인가코드 : "+code);

        JwtLogin jwtLogin = JwtLogin.builder()
                .accesstoken("")
                .isUser(0)
                .isLogin(0)
                .build();

//        엑세스 토큰 받기
        if (code == null) { // 인가코드를 받지 못하면 로그인 실패
            return new ResponseEntity<>(jwtLogin, HttpStatus.UNAUTHORIZED);
        }
        KakaoToken oauthToken = kakaoService.getAccessToken(code);

        if (oauthToken == null) { // 토큰이 없으면 로그인 실패
            return new ResponseEntity<>(jwtLogin, HttpStatus.UNAUTHORIZED);
        }
        // 유저가 없으면 회원가입 + 사용자 정보 요청
        User user = kakaoService.addUser(oauthToken, jwtLogin);

        // 카카오에서 발급받은 access token을 이용해서 jwt 토큰 발급받기
        String jwtAccessToken = kakaoService.getJwtAccess(user);
        String jwtRefreshToken = kakaoService.getJwtRefresh(user);

        if (jwtAccessToken == null || jwtRefreshToken == null) { // jwt의 토큰들이 없으면 로그인 실패
            return new ResponseEntity<>(jwtLogin, HttpStatus.UNAUTHORIZED);
        }

//        // 헤더에 jwt access token 담기
//        HttpHeaders headers = new HttpHeaders();
//        headers.add("Authorization", "Bearer " + jwtAccessToken);
        jwtLogin.setAccesstoken(jwtAccessToken);
        jwtLogin.setIsLogin(1);

        return new ResponseEntity<>(jwtLogin, HttpStatus.OK);
    }

//    // 직접 인가 코드 받기 ( test )
//    @GetMapping("/kakao-login")
////    public String KakaoLogin(@RequestParam("code") String code) {
//    public ResponseEntity KakaoLogin(@RequestParam("code") String code) {
//
//        JwtLogin jwtLogin = JwtLogin.builder()
//                .accesstoken("")
//                .isUser(0)
//                .isLogin(0)
//                .build();
//
////        엑세스 토큰 받기
//        if (code == null) { // 인가코드를 받지 못하면 로그인 실패
//            return new ResponseEntity<>(jwtLogin, HttpStatus.UNAUTHORIZED);
//        }
//        KakaoToken oauthToken = kakaoService.getAccessToken(code);
//
//        System.out.println("토큰 : " + oauthToken);
//
//        if (oauthToken == null) { // 토큰이 없으면 로그인 실패
//            return new ResponseEntity<>(jwtLogin, HttpStatus.UNAUTHORIZED);
//        }
//        // 유저가 없으면 회원가입 + 사용자 정보 요청
//    User user = kakaoService.addUser(oauthToken, jwtLogin);
//        System.out.println("사용자 정보 요청 완료");
//
//    // 카카오에서 발급받은 access token을 이용해서 jwt 토큰 발급받기
//    String jwtAccessToken = kakaoService.getJwtAccess(user);
//    String jwtRefreshToken = kakaoService.getJwtRefresh(user);
//
//        System.out.println("jwt " + jwtAccessToken + " / " + jwtRefreshToken);
//        if (jwtAccessToken == null || jwtRefreshToken == null) { // jwt의 토큰들이 없으면 로그인 실패
//        return new ResponseEntity<>(jwtLogin, HttpStatus.UNAUTHORIZED);
//    }
//
//    // 헤더에 jwt access token 담기
//    HttpHeaders headers = new HttpHeaders();
//        headers.add("Authorization", "Bearer " + jwtAccessToken);
//        jwtLogin.setIsLogin(1);
//
////        return new ResponseEntity<>(jwtLogin, HttpStatus.OK);
//        return ResponseEntity.ok().headers(headers).body(jwtLogin);
//}



// 회원정보조회
@GetMapping("/user")
    public ResponseEntity<UserInfo> userInfo(@RequestHeader("Authorization") String token) {

        // 토큰 유효성 검사
        String passAccess = kakaoService.checkToken(token); // 통과한 access token
        if (passAccess == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        // jwt를 이용해 user 가져오기
        User user = kakaoService.getUser(passAccess);

        return new ResponseEntity<>(UserInfo
                .builder()
                .userName(user.getUserName())
                .userBirth(user.getUserBirth())
                .userHeight(user.getUserHeight())
                .userWeight(user.getUserWeight())
                .userGender(user.getUserGender())
                .userHealth(user.getUserHealth())
                .build(), HttpStatus.OK);
    }

    // 닉네임 조회
    @GetMapping("/user/name")
    public ResponseEntity<String> getUserName(@RequestHeader("Authorization") String token) {

        // 토큰 유효성 검사
        String passAccess = kakaoService.checkToken(token); // 통과한 access token
        if (passAccess == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        // jwt를 이용해 user 가져오기
        User user = kakaoService.getUser(passAccess);

        return new ResponseEntity<>(user.getUserName(), HttpStatus.OK);
    }

    // 회원정보수정
    @PutMapping("/user")
    public ResponseEntity<HttpStatus> adduserInfo(@RequestHeader("Authorization") String token, @RequestBody UserInfo userInfo) {

        // 토큰 유효성 검사
        String passAccess = kakaoService.checkToken(token); // 통과한 access token
        if (passAccess == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        User user = kakaoService.getUser(passAccess);
        System.out.println("정보 들어왔는지 확인 : "+userInfo);
        System.out.println("1userBirth : "+user.getUserBirth());

        user.setUserBirth(userInfo.userBirth);
        user.setUserHeight(userInfo.userHeight);
        user.setUserWeight(userInfo.userWeight);
        user.setUserGender(userInfo.userGender);
        user.setUserHealth(userInfo.userHealth);
        userService.update(user);
        System.out.println("2userBirth : "+user.getUserBirth());

        userService.detailInfo(user);


        System.out.println("회원정보 확인 완료 "+user);


        return new ResponseEntity<>(HttpStatus.OK);
    }

    // 회원 권장량 조회
    @GetMapping("/user/amount")
    public ResponseEntity<UserAmount> userAmount(@RequestHeader("Authorization") String token) {

        // 토큰 유효성 검사
        String passAccess = kakaoService.checkToken(token); // 통과한 access token
        if (passAccess == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        User user = kakaoService.getUser(passAccess);

        userService.detailInfo(user);

        return new ResponseEntity<UserAmount>(userService.userAmount(user), HttpStatus.OK);
    }

    //회원 당, 카페인 최고 섭취량 및 날짜 조회
    @GetMapping("/user/max")
    public ResponseEntity<MaxRecord> userMax(@RequestHeader("Authorization") String token) {

        // 토큰 유효성 검사
        String passAccess = kakaoService.checkToken(token); // 통과한 access token
        if (passAccess == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        User user = kakaoService.getUser(passAccess);

        return new ResponseEntity<>(userService.userMax(user), HttpStatus.OK);
    }

    // 회원탈퇴
    @DeleteMapping("/user")
    public ResponseEntity<Void> deleteUser(@RequestHeader("Authorization") String token) {
        // 토큰 유효성 검사
        String passAccess = kakaoService.checkToken(token); // 통과한 access token
        if (passAccess == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        User user = kakaoService.getUser(passAccess);

        userService.delete(user.getUserId());

        return new ResponseEntity<>(HttpStatus.OK);
    }

    // 로그아웃
    @PutMapping("/logout")
    public ResponseEntity<HttpStatus> Logout(@RequestHeader("Authorization") String token) {

        // 토큰 유효성 검사
        String passAccess = kakaoService.checkToken(token); // 통과한 access token
        if (passAccess == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        User user = kakaoService.getUser(passAccess);

        user.setJwtRefreshToken("");

        return new ResponseEntity<>(HttpStatus.OK);
    }
}