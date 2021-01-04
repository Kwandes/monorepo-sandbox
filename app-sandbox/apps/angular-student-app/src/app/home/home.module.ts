import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Routes, RouterModule } from '@angular/router';

import { CreateStudentComponent } from '../create-student/create-student.component';
import { StudentListComponent } from '../student-list/student-list.component';
import { HomeComponent } from './home.component';

const routes: Routes = [
    { path: '', component: HomeComponent }
];

@NgModule({
    declarations: [HomeComponent, CreateStudentComponent, StudentListComponent],
    imports: [
        CommonModule,
        RouterModule.forChild(routes)
    ],
})
export class HomeModule { }