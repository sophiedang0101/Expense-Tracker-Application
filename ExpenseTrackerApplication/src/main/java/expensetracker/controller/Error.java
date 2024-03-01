package expensetracker.controller;

import java.time.LocalDateTime;

public class Error {
        private LocalDateTime timestamp = LocalDateTime.now();
        private String message;

        public LocalDateTime getTimeStamp(){
            return timestamp;
        }

        public void setTimeStamp(LocalDateTime timestamp){
            this.timestamp = timestamp;
        }

        public String getMessage(){
            return message;
        }

        public void setMessage(String message){
            this.message = message;
        }
}
