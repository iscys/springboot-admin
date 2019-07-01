package com.project.model.school;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class ThirdResult {
        private String Code;
        private String Message;

        public boolean isSuccess(){
                return Code.equals("1");
        }
}
