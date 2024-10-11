import React, { useState } from 'react';
import SideBar from '../../components/molecules/SideBar';
import UserUpdateForm from '../../components/molecules/UserUpdateForm';
import LeaveSave from '../../components/molecules/LeaveSave';

import EmployeeList from '../employeeList/EmployeeList';
import LeaveManage from '../../components/molecules/LeaveManage';
import SaveComment from '../../components/molecules/SaveComment';

import AssignShifts from '../../components/molecules/AssignShifts';


import ManageExpenses from '../../components/molecules/ManageExpenses';


function Dashboard() {
  const [activeItem, setActiveItem] = useState<string>('home');

  return (
    <div className='row'>
      
      <SideBar activeItem={activeItem} setActiveItem={setActiveItem} />
      <div className="col" style={{ marginLeft: '280px', padding: '20px' }}>
        
        {activeItem === 'userupdate' && <UserUpdateForm />}
        {activeItem === 'leavesave' && <LeaveSave />}
        {activeItem === 'shift' && <AssignShifts />}
        {activeItem === 'employeesave' && <EmployeeList />}
        {activeItem === 'leavemanage' && <LeaveManage />}

        {activeItem === 'savecomment' && <SaveComment />}

        {activeItem === 'manageexpenses' && <ManageExpenses />}

      </div>
    </div>
  );
}

export default Dashboard;