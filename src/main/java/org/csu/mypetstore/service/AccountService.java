package org.csu.mypetstore.service;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.csu.mypetstore.domain.Account;
import org.csu.mypetstore.persistence.AccountMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.UUID;

@Service
public class AccountService {
    /**
     * 用户模块
     */

    @Autowired
    private AccountMapper accountMapper;

    /**
     * User query by id
     * @param id
     * @return
     */
    public Account getAccountByID(String id) {
        return  accountMapper.getAccountByID(id);
    }

    /**
     * 注册验证：给定username查看用户
     * @param username
     * @return
     */
    public Account getAccount(String username){return accountMapper.getAccountByUsername(username);}

    /**
     * 登陆验证：username和password，在数据库中看能否查到
     * @param username
     * @param password
     * @return
     */
    public Account getAccount(String username,String password){
        String hashPassword = new SimpleHash("SHA-256", password, username+"reg", 1024).toString();
        Account account = new Account();
        account.setUsername(username);
        account.setPassword(hashPassword);
//        System.out.println("hashPassword is " + hashPassword);
        return accountMapper.getAccountByUsernameAndPassword(account);
    }

    /**
     * 注册账号
     * @param account
     */
    @Transactional
    public Account insertAccount(Account account){
        // user's uuid
        String id = String.format("%010d", new BigInteger(UUID.randomUUID().toString().replace("-", ""), 16));
        Integer _id = Integer.valueOf(id.substring(0, 8));
        account.setId(_id);
        String password = account.getPassword();
        String hashPassword = new SimpleHash("SHA-256", password, id+"reg", 1024).toString();
        account.setPassword(hashPassword);
        accountMapper.insertAccount(account);
        return account;
    }

    /**
     * 更改信息
     * @param account
     */
//    @Transactional
//    public void update_info(Account account){
//        accountMapper.updateAccount(account);
//        accountMapper.updateProfile(account);
//        if (account.getPassword() != null && account.getPassword().length() > 0) {
//            accountMapper.updateSignon(account);
//        }
//    }

}
