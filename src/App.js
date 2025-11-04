import { BrowserRouter as Router, Routes, Route, useLocation } from "react-router-dom";
import Login from "./pages/Login";
import Dashboard from "./pages/Dashboard";
import IssueBook from "./pages/IssueBook";
import ReturnBook from "./pages/ReturnBook";
import ProtectedRoute from "./components/ProtectedRoute";
import AnimatedBackground from "./components/AnimatedBackground";
import Search from "./pages/Search";
import Discover from "./pages/Discover";
import ThemeToggle from "./components/ThemeToggle";
import ProfileMenu from "./components/ProfileMenu";

function ProfileGate() {
  const location = useLocation();
  const token = typeof window !== 'undefined' ? localStorage.getItem("token") : null;
  if (!token || location.pathname === "/") return null;
  return <ProfileMenu />;
}

function App() {
  return (
    <Router>
      <AnimatedBackground />
      <ProfileGate />
      <Routes>
        <Route path="/" element={<Login />} />

        <Route
          path="/dashboard"
          element={
            <ProtectedRoute>
              <Dashboard />
            </ProtectedRoute>
          }
        />

        <Route
          path="/issue"
          element={
            <ProtectedRoute>
              <IssueBook />
            </ProtectedRoute>
          }
        />

        <Route
          path="/return"
          element={
            <ProtectedRoute>
              <ReturnBook />
            </ProtectedRoute>
          }
        />

        <Route
          path="/search"
          element={
            <ProtectedRoute>
              <Search />
            </ProtectedRoute>
          }
        />

        <Route
          path="/discover"
          element={
            <ProtectedRoute>
              <Discover />
            </ProtectedRoute>
          }
        />
      </Routes>
      {/* Theme Toggle */}
      <ThemeToggle />
    </Router>
  );
}

export default App;
