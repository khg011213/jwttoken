package com.example._6.Controller;

import com.example._6.Entity.RefeshEntity;
import com.example._6.JWT.JWTUtil;
import com.example._6.Repository.RefreshRepository;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;

@Controller
@ResponseBody
@RequiredArgsConstructor
public class ReissueController {
    private final JWTUtil jwtUtil;

    private final RefreshRepository refreshRepository;

    @PostMapping("/reissue")
    public ResponseEntity<?> reissue(HttpServletRequest request, HttpServletResponse response) {


            //get refresh token
            String refresh = null;
            Cookie[] cookies = request.getCookies();
            for (Cookie cookie : cookies) {

                if (cookie.getName().equals("refresh")) {

                    refresh = cookie.getValue();
                }
            }

            if (refresh == null) {

                //response status code
                return new ResponseEntity<>("refresh token null", HttpStatus.BAD_REQUEST);
            }

            //expired check
            try {
                jwtUtil.isExpired(refresh);
            } catch (ExpiredJwtException e) {

                //response status code
                return new ResponseEntity<>("refresh token expired", HttpStatus.BAD_REQUEST);
            }

            // 토큰이 refresh인지 확인 (발급시 페이로드에 명시)
            String category = jwtUtil.getCategory(refresh);

            if (!category.equals("refresh")) {

                //response status code
                return new ResponseEntity<>("invalid refresh token", HttpStatus.BAD_REQUEST);
            }

            Boolean isExist = refreshRepository.existsByRefresh(refresh);
            if (!isExist){
                return new ResponseEntity<>("invalid refresh token", HttpStatus.BAD_REQUEST);
            }

            String username = jwtUtil.getUsername(refresh);
            String role = jwtUtil.getRole(refresh);

            //make new JWT
            String newAccess = jwtUtil.createJwt("access", username, role, 600000L);
            String newRefresh = jwtUtil.createJwt("refresh", username, role, 86400000L);

            refreshRepository.deleteByRefresh(refresh);
            addRefreshEntity(username, newRefresh, 86400000L);

            //response
            response.setHeader("access", newAccess);
            response.addCookie(CreateCookie("refresh" , newRefresh));

            return new ResponseEntity<>(HttpStatus.OK);

        }

    private Cookie CreateCookie(String key, String value) {
        Cookie cookie = new Cookie(key,value);
        cookie.setMaxAge(24*60*60);
        cookie.setHttpOnly(true);

        return cookie;
    }

    private void addRefreshEntity(String username, String refresh, Long expiredMs){
        Date date = new Date(System.currentTimeMillis() + expiredMs);

        RefeshEntity refeshEntity = new RefeshEntity();
        refeshEntity.setUsername(username);
        refeshEntity.setRefresh(refresh);
        refeshEntity.setExpiration(date.toString());

        refreshRepository.save(refeshEntity);
    }






}