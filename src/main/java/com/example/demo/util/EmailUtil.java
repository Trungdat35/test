package com.example.demo.util;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class EmailUtil {

    @Autowired
    private JavaMailSender javaMailSender;

    public void sendOtpEmail(String email, String otp) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);
        mimeMessageHelper.setTo(email);
        mimeMessageHelper.setSubject("Verify OTP");
        mimeMessageHelper.setText(otp);
        javaMailSender.send(mimeMessage);
    }

    public void sendOrderEmail(int maDh, String tenSp, int soLuong, LocalDate ngaytao, String httt, String ttdh, double tong, String email) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);
        mimeMessageHelper.setTo(email);
        mimeMessageHelper.setSubject("Verify OTP");
        mimeMessageHelper.setText(
                """
                                      
                        <table>
                                                                          <tr>
                                                                              <th>Mã đơn hàng</th>
                                                                              <th>Tên sản phẩm</th>
                                                                              <th>Số lượng</th>
                                                                              <th>Ngày tạo</th>
                                                                              <th>Phương thức thanh toán</th>
                                                                              <th>Trạng thái đơn hàng</th>
                                                                              <th>Tổng giá</th>
                                                                          </tr>
                                                                          <tr>
                                                                              <td>%d</td>
                                                                              <td>%s</td>
                                                                              <td>%d</td>
                                                                              <td>%s</td>
                                                                              <td>%s</td>
                                                                              <td>%s</td>
                                                                              <td>%.2f</td>
                                                                          </tr>
                                                                      </table>
                                       """.formatted(maDh, tenSp, soLuong, ngaytao.toString(), httt, ttdh, tong), true);
        javaMailSender.send(mimeMessage);
    }
}
