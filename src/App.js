import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import Login from "./pages/Login";
import Dashboard from "./pages/Dashboard";
import IssueBook from "./pages/IssueBook";
import ReturnBook from "./pages/ReturnBook";
import ProtectedRoute from "./components/ProtectedRoute";
import AnimatedBackground from "./components/AnimatedBackground";

function App() {
  return (
    <Router>
      <AnimatedBackground />
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
      </Routes>
    </Router>
  );
}

export default App;
