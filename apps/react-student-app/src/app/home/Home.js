import React from 'react';

import CreateStudent from "../create_student/CreateStudent";
import StudentList from "../student_list/StudentList";
import './Home.css'

// Base component that defines the look of the page. Every component leads to here
export default class Home extends React.Component
{
    render()
    {
        return (
            <div id={'home'}>
                <div id={'left'}>
                    <h3>Sonito Student Management System</h3>
                    <CreateStudent/>
                    <h6>Tip. Supervisors can be looked up and/or created directly via the API</h6>
                </div>
                <div id={'right'}>
                    <StudentList/>
                </div>
            </div>
        )
    }
}