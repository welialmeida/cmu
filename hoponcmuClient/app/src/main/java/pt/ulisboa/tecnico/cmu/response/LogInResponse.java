package pt.ulisboa.tecnico.cmu.response;

public class LogInResponse extends Response {

    private static final long serialVersionUID = 734457624276534179L;
    private String message;
    private double uniqueId;

    public LogInResponse(double uniqueId) {
        super();
        this.message = "LogIn success";
        this.uniqueId = uniqueId;
    }

    public double getUniqueId() {
        return this.uniqueId;
    }

    public String getMessage() {
        return this.message;
    }
}
