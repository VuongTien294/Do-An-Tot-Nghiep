package com.doantotnghiep.demo.service;

import com.doantotnghiep.demo.entity.BillProduct;

import java.util.List;

public interface MailService {
    void sendMail(String user_last_name,String responseMessage, String receiver);

    void sendBill(List<BillProduct> list, String receiver);
}
