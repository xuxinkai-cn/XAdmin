package cn.xuxinkai.modules.common.util;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.xuxinkai.modules.security.config.bean.SysSecurityProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

/**
 * Token 相关工具类
 *
 * @author xuxinkai
 * @date 2020/12/30
 */
@Slf4j
@Component
public class SysTokenUtils {

    private final SysSecurityProperties sysSecurityProperties;

    public SysTokenUtils(SysSecurityProperties sysSecurityProperties) {
        this.sysSecurityProperties = sysSecurityProperties;
    }

    /**
     * 创建jwt令牌
     *
     * @param subject 代表这个JWT的主体，即它的所有人,一般存入用户名
     * @param claims  这个是一个json格式的字符串，可以存放什么userid，roldid之类的，作为用户的唯一标志
     * @return {@link String}
     */
    public String createToken(String subject, Map<String, Object> claims) {
        // 生成JWT的时间
        long nowMillis = System.currentTimeMillis();

        // 下面就是在为payload添加各种标准声明和私有声明了
        JwtBuilder builder = Jwts.builder()
                // 加入ID确保生成的 Token 都不一致
                .setId(IdUtil.simpleUUID())
                // 如果有私有声明，一定要先设置这个自己创建的私有的声明，这个是给builder的claim赋值，一旦写在标准的声明赋值之后，就是覆盖了那些标准的声明的
                .setClaims(claims)
                // iat: jwt的签发时间
                .setIssuedAt(new Date(nowMillis))
                .setSubject(subject)
                // 设置签名使用的签名算法和签名使用的秘钥,一旦客户端得知这个secret, 那就意味着客户端是可以自我签发jwt了。
                .signWith(Keys.hmacShaKeyFor(StrUtil.bytes(sysSecurityProperties.getTokenSecret())));

        // 设置过期时间
        if (sysSecurityProperties.getTokenTtl() >= 0) {
            long expMillis = nowMillis + sysSecurityProperties.getTokenTtl();
            builder.setExpiration(new Date(expMillis));
        }
        return builder.compact();
    }

    /**
     * 解析令牌
     * 解密jwt
     *
     * @param jwt jwt
     * @return {@link Claims}
     */
    public Claims parseToken(String jwt) {
        return Jwts
                .parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(StrUtil.bytes(sysSecurityProperties.getTokenSecret())))
                .build()
                .parseClaimsJws(jwt)
                .getBody();
    }

    /**
     * 是过期
     * 是否已过期
     *
     * @param token 令牌
     * @return boolean
     */
    public boolean isExpiration(String token){
        try {
            return parseToken(token).getExpiration().before(new Date());
        } catch (ExpiredJwtException e) {
            return true;
        }
    }

    /**
     * 依据Token 获取鉴权信息
     *
     * @param token /
     * @return /
     */
    public Authentication getAuthentication(String token) {
        Claims claims = parseToken(token);
        User principal = new User(claims.getSubject(), "******", new ArrayList<>());
        return new UsernamePasswordAuthenticationToken(principal, token, new ArrayList<>());
    }
}

