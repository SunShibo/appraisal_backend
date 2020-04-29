package com.wisewin.api.util.jwtUtil;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtUtil {
    private static final long EXPIRE_TIME=0;
    private static final String TOKEN_SECRET="3585751fccbf7574e266bf4ac5e14485";


    /**
     * 设置信息
     * @param username
     * @param userId
     * @return
     */
    public static String sign(String username, String userId){

        try {
            Date date=new Date(System.currentTimeMillis()+EXPIRE_TIME);
            Algorithm algorithm=Algorithm.HMAC256(TOKEN_SECRET);
            Map<String,Object> header=new HashMap<String, Object>();
            header.put("typ","JWT");
            header.put("alg","HS256");
            String result= JWT.create().withHeader(header).
                    withClaim("userName",username).
                    withClaim("userId",userId).
                    withExpiresAt(date).sign(algorithm);

            return  result;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return  null;
    }


    /**
     * 验证是否成功
     */
     public static String verify(String token){
         Algorithm algorithm= null;
         try {
             algorithm = Algorithm.HMAC256(TOKEN_SECRET);
             JWTVerifier  verifier = JWT.require(algorithm).build();
             DecodedJWT jwt=verifier.verify(token);
             if(jwt!=null || jwt.getClaims()!=null){
                 Map<String, Claim> claims = jwt.getClaims();
                 Claim userId = claims.get("userId");
                 return userId.asString();
             }
         } catch (UnsupportedEncodingException e) {
            return null;
         }catch (Exception e){
             return null;
         }
         return null;

     }

     public static void main(String[] args){
         String hexiaowen = sign("hexiaowen", "1");
         System.out.println(hexiaowen);
         String verify = verify(hexiaowen);
         System.out.println(verify);
     }

}
