package dev.hotdeals.api.student;

public class StudentUml
{
    public String toString()
    {
        return  "    +--------------------------------------+\n" +
                "    |   student                            |\n" +
                "    +--------------------------------------+\n" +
                "    |   id : INTEGER(20)                   |\n" +
                "    |   name: VARCHAR(160)                 |\n" +
                "    |   email: VARCHAR(255)                |\n" +
                "    |   supervisor_id: INTEGER(20)         |\n" +
                "    |   CONSTRAINT fk_student_super        |\n" +
                "    |   FOREIGN KEY (supervisor_id)        |\n" +
                "    |   REFERENCES api.supervisor (id)  |\n" +
                "    +--------------------------------------+";
    }
}
