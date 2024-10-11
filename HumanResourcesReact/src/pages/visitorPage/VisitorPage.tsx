import React from 'react'
import './VisitorPage.css'
import logo from '../../img/navbarlogo.png'
import denemepng from '../../img/iceland-5120x2880-5k-4k-wallpaper-osx-forest-apple-waterfall-173.jpg'
import team from '../../img/team.png'
import ux from '../../img/UX-vs-UI-Design.png'
import happyCustomer from '../../img/happy-customers-1080x675.jpg'
import { useNavigate } from 'react-router-dom';
import NavBar from '../../components/molecules/NavBar'
import Hero from '../../components/molecules/Hero'
import Highlights from '../../components/molecules/Highlights'
import Testimonials from '../../components/molecules/Testimonials'
function VisitorPage() {
    



  return (
    <>
    <div className="row">
        <NavBar/>
    </div>
    <div className="row">
      <Hero/>
    </div>
    
    <div className='row '>
      <Highlights />
    </div>

    <div className='row '>
      <Testimonials />
    </div>
    
    </>
    
  )
}

export default VisitorPage