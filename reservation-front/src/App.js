import React from 'react';
import { BrowserRouter, Routes, Route, Navigate } from 'react-router-dom';
import Sidebar from './component/common/Sidebar';
import FooterComponent from './component/common/Footer';
import LoginPage from './component/auth/LoginPage';
import RegisterPage from './component/auth/RegisterPage';
import HomePage from './component/home/HomePage';
import AllLabsPage from './component/reserving_labs/AllLabsPage';
import LabDetailsReservingPage from './component/reserving_labs/LabDetailsPage';
import FindReservingPage from './component/reserving_labs/FindReservingPage';
import AdminPage from './component/admin/AdminPage';
import ManageLabPage from './component/admin/ManageLabPage';
import EditLabPage from './component/admin/EditLabPage';
import AddLabPage from './component/admin/AddLabPage';
import ManageReservingsPage from './component/admin/ManageReservingsPage';
import EditReservingPage from './component/admin/EditReservingPage';
import ProfilePage from './component/profile/ProfilePage';
import EditProfilePage from './component/profile/EditProfilePage';
import { ProtectedRoute, AdminRoute } from './service/guard';

function App() {
  return (
    <BrowserRouter>
      <div className="App">
        <Sidebar />
        <div className="content">
          <Routes>
            {/* Public Routes */}
            <Route path="/home" element={<HomePage />} />
            <Route path="/login" element={<LoginPage />} />
            <Route path="/register" element={<RegisterPage />} />
            <Route path="/labs" element={<AllLabsPage />} />
            <Route path="/find-reserving" element={<FindReservingPage />} />

            {/* Protected Routes */}
            <Route path="/lab-details-reserve/:labId" element={<ProtectedRoute element={<LabDetailsReservingPage />} />} />
            <Route path="/profile" element={<ProtectedRoute element={<ProfilePage />} />} />
            <Route path="/edit-profile" element={<ProtectedRoute element={<EditProfilePage />} />} />

            {/* Admin Routes */}
            <Route path="/admin" element={<AdminRoute element={<AdminPage />} />} />
            <Route path="/admin/manage-labs" element={<AdminRoute element={<ManageLabPage />} />} />
            <Route path="/admin/edit-lab/:labId" element={<AdminRoute element={<EditLabPage />} />} />
            <Route path="/admin/add-lab" element={<AdminRoute element={<AddLabPage />} />} />
            <Route path="/admin/manage-reservings" element={<AdminRoute element={<ManageReservingsPage />} />} />
            <Route path="/admin/edit-reserving/:reservingCode" element={<AdminRoute element={<EditReservingPage />} />} />

            {/* Fallback Route */}
            <Route path="*" element={<Navigate to="/login" />} />
          </Routes>
        </div>
        <FooterComponent />
      </div>
    </BrowserRouter>
  );
}

export default App;
