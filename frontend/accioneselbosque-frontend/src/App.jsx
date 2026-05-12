import {
  BrowserRouter as Router,
  Routes,
  Route,
  Navigate,
} from "react-router-dom";
import RegisterPage from "./modules/auth/pages/RegisterPage";
import GoogleCallback from "./modules/auth/pages/GoogleCallback";
import "./App.css";

// Componentes Placeholder para las rutas de destino
const Home = () => (
  <div style={{ padding: "40px", color: "white", textAlign: "center" }}>
    <h1>Bienvenido a Acciones El Bosque</h1>
    <p>Has iniciado sesión correctamente.</p>
    <button
      onClick={() => {
        localStorage.clear();
        window.location.reload();
      }}
      style={{ marginTop: "20px", padding: "10px 20px", cursor: "pointer" }}
    >
      Cerrar Sesión
    </button>
  </div>
);

const CompleteProfile = () => (
  <div style={{ padding: "40px", color: "white", textAlign: "center" }}>
    <h1>Completar Perfil</h1>
    <p>¡Bienvenido! Por favor completa tus datos para empezar a invertir.</p>
    <button
      onClick={() => (window.location.href = "/")}
      style={{ marginTop: "20px", padding: "10px 20px", cursor: "pointer" }}
    >
      Ir al Inicio
    </button>
  </div>
);

function App() {
  return (
    <Router>
      <div className="app-container">
        <Routes>
          {/* Ruta de Registro */}
          <Route path="/registro" element={<RegisterPage />} />

          {/* Ruta de Callback de Google */}
          <Route path="/auth/google/callback" element={<GoogleCallback />} />

          {/* Ruta de Completar Perfil (Nuevos usuarios) */}
          <Route path="/completar-perfil" element={<CompleteProfile />} />

          {/* Ruta Home (Usuarios existentes) */}
          <Route path="/home" element={<Home />} />

          {/* Redirige la raíz al registro si no hay sesión */}
          <Route path="/" element={<Navigate to="/registro" replace />} />

          {/* Redirección por defecto */}
          <Route path="*" element={<Navigate to="/registro" replace />} />
        </Routes>
      </div>
    </Router>
  );
}

export default App;
