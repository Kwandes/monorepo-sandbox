import React from 'react';
import './CreateStudent.css'
import HttpService from "../service/HttpService";

// Used within home, is a left side menu thatv allows creation of a student
export default class CreateStudent extends React.Component
{

    constructor(props)
    {
        super(props);
        this.createUser = this.createUser.bind(this);
        this.getSupervisorList = this.getSupervisorList.bind(this);

        this.state = {
            name: "",
            email: "",
            supervisorId: null,
            supervisors: [],
        }
    }

    componentDidMount()
    {
        this.getSupervisorList();
    }

    getSupervisorList()
    {
        HttpService
            .get("/supervisor")
            .then((response) =>
            {
                console.log("getSupervisorList Response :");
                console.log(response.data);

                this.setState({
                    supervisors: response.data,
                });
            })
            .catch((e) =>
            {
                console.log(e);
            });
    }

    createUser()
    {
        console.log(this.state.name + " " + this.state.email + " " + this.state.supervisorId)

        if (this.state.name === "" || this.state.email === "" || this.state.supervisorId == null)
        {
            alert("You have to fill in all fields before creating a student");
            return;
        }

        HttpService.post("/student", {
            name: this.state.name,
            email: this.state.email,
            supervisor: {
                id: this.state.supervisorId
            }
        })
            .then((response) =>
            {
                console.log("createStudent Response :");
                console.log(response.data);
            })
            .catch((e) =>
            {
                console.log(e);
            });

        window.location.reload(false);
    }

    updateInfo = (type, text) =>
    {
        if (type === 'name')
        {
            this.setState({name: text});
        } else if (type === 'email')
        {
            this.setState({email: text});
        } else if (type === 'supervisorId')
        {
            this.setState({supervisorId: text});
        }
    }


    render()
    {
        return (
            <div id={'StudentContainer'}>
                <h3>Create your very own Student</h3>
                <input type="text" placeholder={"name"} onChange={e => this.updateInfo('name', e.target.value)}/>
                <input type="text" placeholder={"email"} onChange={e => this.updateInfo('email', e.target.value)}/>


                <select id={"SupervisorSelect"} onChange={e => this.setState({supervisorId: e.target.value})}>
                    <option value={null}>supervisor</option>
                    {this.state.supervisors.map((supervisor) => (
                        <option key={supervisor.id}
                                value={supervisor.id}>{supervisor.id + ' | ' + supervisor.name + ' | ' + supervisor.email}
                        </option>
                    ))}
                </select>

                <input type={'button'} id={'CreateStudentBtn'} value={'Create Student'}
                       onClick={() => this.createUser()}/>
            </div>
        )
    }
}