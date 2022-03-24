package server.websockets.dto;

public class Dto {


    private String message;

    /**
     * method for returning the message.
     * @return message to be returned
     */
    public String getMessage() {

        return message;

    }

    /**
     * method for setting the message.
     * @param message message to be set
     */
    public void setMessage(String message) {

        this.message = message;

    }


}
