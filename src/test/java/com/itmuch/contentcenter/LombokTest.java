package com.itmuch.contentcenter;

import com.itmuch.contentcenter.pojo.po.MidUserShare;
import lombok.*;
import lombok.extern.slf4j.Slf4j;


@Slf4j
public class LombokTest {
    public static void main(String[] args) {

        MidUserShare.builder();


        //建造者模式
        UserRegisterDTO.UserRegisterDTOBuilder password = UserRegisterDTO.builder()
                .email("嘻嘻嘻1");

        log.info("你好！！！！！，"+password);


    }
}

/*@Getter
@Setter
@ToString
@EqualsAndHashCode*/

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
class UserRegisterDTO{

    private String email;
    private String password;
    private String confirmPassword;

}





