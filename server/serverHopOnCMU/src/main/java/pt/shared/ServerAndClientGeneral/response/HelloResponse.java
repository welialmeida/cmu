package pt.shared.ServerAndClientGeneral.response;

public class HelloResponse extends Response {

    private static final long serialVersionUID = 734457624276534179L;
    private String message;

    public HelloResponse() {
        this.message = "ping accepted"+"\n";
    }

    @Override
    public void handle(ResponseHandler rh) {
        rh.handle(this);
    }

    public String toString() {
        return getMessage();
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }
}
