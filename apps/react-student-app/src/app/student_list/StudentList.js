import React from 'react';
import HttpService from "../service/HttpService";
import './StudentList.css'
import StudentRow from "./StudentRow";

// Student list is displayed on the right side of the screen and contains information about students (duh)
export default class StudentList extends React.Component
{
    constructor(props)
    {
        super(props);
        this.state = {
            students: []
        }
    }

    componentDidMount()
    {
        this.getStudentList();
    }

    getStudentList()
    {
        HttpService
            .get("/student")
            .then((response) =>
            {
                console.log("getStudentList Response :");
                console.log(response.data);

                this.setState({
                    students: response.data,
                });
            })
            .catch((e) =>
            {
                console.log(e);
            });
    }

    render()
    {
        // Split the students list into specific users and fill up the list
        const studentList = this.state.students.map((student, index) =>
        {
            let supervisor = student.supervisor;
            if (!supervisor)
            {
                supervisor = {
                    id: 'No supervisor',
                    name: 'No supervisor',
                    email: ''
                }
            }

            return (
                <div className={'StudentInfoRow'} key={index}>
                    <StudentRow id={student.id} name={student.name} email={student.email} supervisor={supervisor}/>
                </div>
            )
        });

        return (
            <div id={'StudentList'}>
                {studentList}
            </div>
        )
    }
}