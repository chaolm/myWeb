package com.dripop.util;

import com.bean.ChannelType;
import com.bean.SysOperType;
import com.dripop.bean.TokenDto;
import com.dripop.core.exception.ServiceException;
import com.dripop.core.util.RedisUtil;
import com.dripop.core.util.SessionUtil;
import com.dripop.core.util.SpringContextUtil;
import com.dripop.core.util.StringUtil;
import com.dripop.dao.SysOrgDao;
import com.dripop.dto.InterDtoCUser;
import com.dripop.dto.InterDtoUser;
import com.dripop.entity.TCustomer;
import com.dripop.entity.TSysOper;
import com.dripop.entity.TSysOrg;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Random;
import java.util.Set;

/**
 * Created by liyou on 2017/12/1.
 */
public class UserUtil {

    public static final String USER_TOKEN_KEY_HEADER = "user:session";

    public static final String MANAGE_TOKEN_REQ_KEY = "MANAGE_TOKEN";

    public static final String SALE_TOKEN_REQ_KEY = "SALE_TOKEN";

    public static final String CUSTOMER_TOKEN_REQ_KEY = "CUSTOMER_TOKEN";

    public static final Integer ADMIN_USER_EXPIRE = 1800;

    public static final Integer SALE_USER_EXPIRE = 6 * 60 * 60;

    public static final Integer CUSTOMER_USER_EXPIRE = 15 * 24 * 60 * 60;

    public static TCustomer currentCustomer() {
        String token = SessionUtil.getRequest(CUSTOMER_TOKEN_REQ_KEY, String.class);
        if(StringUtil.isBlank(token)) {
            return null;
        }
        Set<String> keySet = RedisUtil.INSTANCE.keys(UserUtil.USER_TOKEN_KEY_HEADER + ":*:customer:*");
        if(CollectionUtils.isEmpty(keySet)) {
            return null;
        }
        return RedisUtil.INSTANCE.get(token, TCustomer.class);
//        TCustomer customer = new TCustomer();
//        customer.setId(100020000019152L);
//        return customer;
    }

    public static void removeCustomer(Long userId) {
        userId = userId == null ? (currentCustomer() == null ? null : currentCustomer().getId()) : userId;
        if(userId == null) {
            return;
        }
        Set<String> keySet = RedisUtil.INSTANCE.keys(UserUtil.USER_TOKEN_KEY_HEADER + ":*:customer:" + userId);
        for (String key : keySet) {
            if(RedisUtil.INSTANCE.exists(RedisUtil.INSTANCE.get(key))) {
                RedisUtil.INSTANCE.delete(RedisUtil.INSTANCE.get(key));
            }
            RedisUtil.INSTANCE.delete(key);
        }
    }

    public static void removeCustomer(Long userId, ChannelType channelType) {
        userId = userId == null ? (currentCustomer() == null ? null : currentCustomer().getId()) : userId;
        if(userId == null) {
            return;
        }
        Set<String> keySet = RedisUtil.INSTANCE.keys(UserUtil.USER_TOKEN_KEY_HEADER + ":*:customer:" + userId+":channel:"+channelType.getValue());
        for (String key : keySet) {
            if(RedisUtil.INSTANCE.exists(RedisUtil.INSTANCE.get(key))) {
                RedisUtil.INSTANCE.delete(RedisUtil.INSTANCE.get(key));
            }
            RedisUtil.INSTANCE.delete(key);
        }
    }

    public static InterDtoUser currentAdminUser() {
        String token = SessionUtil.getRequest(MANAGE_TOKEN_REQ_KEY, String.class);
        if(StringUtil.isBlank(token)) {
            return null;
        }
        Set<String> keySet = RedisUtil.INSTANCE.keys(UserUtil.USER_TOKEN_KEY_HEADER + ":*:user:*");
        if(CollectionUtils.isEmpty(keySet)) {
            return null;
        }
        return RedisUtil.INSTANCE.get(token, InterDtoUser.class);
    }

    public static void removeAdminUser(Long operId) {
        operId = operId == null ? (currentAdminUser() == null ? null : currentAdminUser().getId()) : operId;
        if(operId == null) {
            return;
        }
        Set<String> keySet = RedisUtil.INSTANCE.keys(UserUtil.USER_TOKEN_KEY_HEADER + ":*:user:" + operId);
        for (String key : keySet) {
            if(RedisUtil.INSTANCE.exists(RedisUtil.INSTANCE.get(key))) {
                RedisUtil.INSTANCE.delete(RedisUtil.INSTANCE.get(key));
            }
            RedisUtil.INSTANCE.delete(key);
        }
    }

    public static void removeAdminByRole(Long roleId) {
        Set<String> keySet = RedisUtil.INSTANCE.keys("*:role:" + roleId+":*");
        for (String key : keySet) {
            if(RedisUtil.INSTANCE.exists(RedisUtil.INSTANCE.get(key))) {
                RedisUtil.INSTANCE.delete(RedisUtil.INSTANCE.get(key));
                RedisUtil.INSTANCE.delete(RedisUtil.INSTANCE.get(key));
            }
            RedisUtil.INSTANCE.delete(key);
        }
    }

    public static InterDtoUser currentSaleUser() {
        String token = SessionUtil.getRequest(SALE_TOKEN_REQ_KEY, String.class);
        if(StringUtil.isBlank(token)) {
            return null;
        }
        Set<String> keySet = RedisUtil.INSTANCE.keys(UserUtil.USER_TOKEN_KEY_HEADER + ":*:sale:*");
        if(CollectionUtils.isEmpty(keySet)) {
            return null;
        }
        return RedisUtil.INSTANCE.get(token, InterDtoUser.class);
    }

    public static void removeSaleUser(Long operId) {
        operId = operId == null ? (currentSaleUser() == null ? null : currentSaleUser().getId()) : operId;
        if(operId == null) {
            return;
        }
        Set<String> keySet = RedisUtil.INSTANCE.keys(UserUtil.USER_TOKEN_KEY_HEADER + ":*:sale:" + operId);
        for (String key : keySet) {
            if(RedisUtil.INSTANCE.exists(RedisUtil.INSTANCE.get(key))) {
                RedisUtil.INSTANCE.delete(RedisUtil.INSTANCE.get(key));
            }
            RedisUtil.INSTANCE.delete(key);
        }
    }

    public static TokenDto refreshToken(TCustomer cust, ChannelType channelType){
        //移除上次登录的token信息
        UserUtil.removeCustomer(cust.getId(), channelType);
        String key = null;
        for (int i = 0; i < 5; i++) {
            key = UserUtil.USER_TOKEN_KEY_HEADER + ":" + StringUtil.getUUID();
            if(!RedisUtil.INSTANCE.exists(key)) {
                break;
            }
        }
        if(StringUtil.isBlank(key)) {
            throw new ServiceException("系统繁忙，请稍后再试");
        }
        RedisUtil.INSTANCE.set(key, cust, UserUtil.CUSTOMER_USER_EXPIRE);

        StringBuffer userRoleKey = new StringBuffer();
        userRoleKey.append(key).append(":customer:").append(cust.getId()).append(":channel:").append(channelType.getValue());
        RedisUtil.INSTANCE.set(userRoleKey.toString(), key, UserUtil.CUSTOMER_USER_EXPIRE);
        TokenDto tokenDto = new TokenDto();
        tokenDto.setToken(key);
        return tokenDto;
    }

    /**
     * 重置C端用户信息过期时间
     */
    public static void customerExpireReset() {
        String token = SessionUtil.getRequest().getAttribute(CUSTOMER_TOKEN_REQ_KEY).toString();
        Set<String> keySet = RedisUtil.INSTANCE.keys(token + ":*");
        keySet.add(token);
        for (String key : keySet) {
            RedisUtil.INSTANCE.expire(key, CUSTOMER_USER_EXPIRE);
        }
    }

    /**
     * 重置redis管理员信息过期时间
     */
    public static void adminUserExpireReset() {
        String token = SessionUtil.getRequest().getAttribute(MANAGE_TOKEN_REQ_KEY).toString();
        Set<String> keySet = RedisUtil.INSTANCE.keys(token + ":*");
        keySet.add(token);
        for (String key : keySet) {
            RedisUtil.INSTANCE.expire(key, ADMIN_USER_EXPIRE);
        }
    }

    /**
     * 重置redis门店用户信息过期时间
     */
    public static void saleUserExpireReset() {
        String token = SessionUtil.getRequest().getAttribute(SALE_TOKEN_REQ_KEY).toString();
        Set<String> keySet = RedisUtil.INSTANCE.keys(token + ":*");
        keySet.add(token);
        for (String key : keySet) {
            RedisUtil.INSTANCE.expire(key, ADMIN_USER_EXPIRE);
        }
    }

    public static TokenDto refreshToken(TSysOper sysOper, Long roleId) {
        TokenDto tokenDto = null;
        if(sysOper.getType().equals(SysOperType.ADMIN.getValue())) {
            UserUtil.removeAdminUser(sysOper.getOperId());
            String key = null;
            for (int i = 0; i < 5; i++) {
                key = UserUtil.USER_TOKEN_KEY_HEADER + ":" + StringUtil.getUUID();
                if(!RedisUtil.INSTANCE.exists(key)) {
                    break;
                }
            }
            if(StringUtil.isBlank(key)) {
                throw new ServiceException("系统繁忙，请稍后再试");
            }
            RedisUtil.INSTANCE.set(key, sysOper, UserUtil.ADMIN_USER_EXPIRE);
            StringBuffer userRoleKey = new StringBuffer();
            userRoleKey.append(key).append(":role:").append(roleId).append(":user:").append(sysOper.getOperId());
            RedisUtil.INSTANCE.set(userRoleKey.toString(), key, UserUtil.ADMIN_USER_EXPIRE);
            tokenDto = new TokenDto();
            tokenDto.setToken(key);
            tokenDto.setName(sysOper.getOperName());
        }else if(sysOper.getType().equals(SysOperType.SELLER.getValue())) {
            UserUtil.removeSaleUser(sysOper.getOperId());
            String key = null;
            for (int i = 0; i < 5; i++) {
                key = UserUtil.USER_TOKEN_KEY_HEADER + ":" + StringUtil.getUUID();
                if(!RedisUtil.INSTANCE.exists(key)) {
                    break;
                }
            }
            RedisUtil.INSTANCE.set(key, sysOper, UserUtil.SALE_USER_EXPIRE);
            StringBuffer userRoleKey = new StringBuffer();
            userRoleKey.append(key).append(":sale:").append(sysOper.getOperId());
            RedisUtil.INSTANCE.set(userRoleKey.toString(), key, UserUtil.SALE_USER_EXPIRE);
            tokenDto = new TokenDto();
            tokenDto.setToken(key);
            tokenDto.setName(sysOper.getOperName());
            tokenDto.setOrgId(sysOper.getOrgId());
            SysOrgDao sysOrgDao = SpringContextUtil.getContext().getBean(SysOrgDao.class);
            TSysOrg sysOrg = sysOrgDao.get(sysOper.getOrgId());
            tokenDto.setOrgName(sysOrg.getShortName());
        }
        return tokenDto;
    }

    public static String getCharAndNumr(int length) {
        String val = "";
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            // 输出字母还是数字
            String charOrNum = random.nextInt(2) % 2 == 0 ? "char" : "num";
            // 字符串
            if ("char".equalsIgnoreCase(charOrNum)) {
                // 取得大写字母还是小写字母
                int choice = random.nextInt(2) % 2 == 0 ? 65 : 97;
                val += (char) (choice + random.nextInt(26));
            } else if ("num".equalsIgnoreCase(charOrNum)) { // 数字
                val += String.valueOf(random.nextInt(10));
            }
        }
        return val;
    }
}
