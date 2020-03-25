package org.csu.mypetstore.service;

import org.csu.mypetstore.domain.Account;
import org.csu.mypetstore.persistence.AccountMapper;
import org.springframework.beans.factory.annotation.Autowired;

public class AccountService {
    /**
     * 用户模块
     */

    @Autowired
    private AccountMapper accountMapper;

    /**
     * 注册验证：给定username查看用户
     * @param username
     * @return
     */
    public Account getAccount(String username){return accountMapper.getAccountByUsername(username);}

    /**
     * 登陆验证：给定一个account，在数据库中看能否查到
     * @param account
     * @return
     */
    public Account getAccount(Account account){return accountMapper.getAccountByUsernameAndPassword(account);}

}
