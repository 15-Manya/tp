package seedu.address.model.person;

public class Attendance {

    public boolean status;

    public Attendance() {
        this.status = false;
    }

    public Attendance(boolean status) {
        this.status = status;
    }

    public boolean getStatus() {
        return status;
    }
    public void setStatus(boolean status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return status ? "Checked-In" : "Not Checked-In";
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof Attendance
                && status == ((Attendance) other).status);
    }
}
