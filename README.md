# MyPetStore

## Constructed by SpringMVC,Spring and MyBatis

## Task
 - [x] Catalog  

 - [x] Account
   * 注册新用户模块仍有bug--已解决

 - [x] Order
    - [x] Order
    - [x] Cart
        * 购物车添加新的item仍有bug--已解决
        * 购物车提交订单处仍有bug--已解决，完成订单后数据库更改。
        * 查看最新完成的一笔交易订单有bug，返回的每个lineItem的item都为null--已解决️

 - [x] 商品有限数量的修改。(在提交并确认订单之后数据库中item的quantity修改)

 ## 已解决的问题：

* 表示层基本重构。
* 持久化层基本重构。


 ## 待解决问题：
 * 用户注册仍有bug。--已解决

 * 完成订单之后，显示刚才完成订单的界面无法获取lineitem的item属性。--已解决

 * 更改商品有限数量模块。--已解决
 
 * 新增bug：在没有登陆的情况下提交订单报错。--已解决。
 
 * 商品图片显示bug。--已解决 

 * 附加任务

    * 用户管理模块利用MD5或其他的加密算法。
    * 用户模块和登陆模块添加验证码。
    * 订单模块支付功能，使用支付宝或者微信接口。

   



 




