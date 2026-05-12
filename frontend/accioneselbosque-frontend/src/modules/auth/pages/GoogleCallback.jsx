/**
 * GoogleCallback.jsx
 * Capa de presentación / lógica de flujo — Módulo Auth
 * Procesa el código de autorización de Google, se comunica con el backend y redirige al usuario.
 */

import { useEffect, useState, useRef, useCallback } from "react";
import { useNavigate, useLocation } from "react-router-dom";
import { registerWithGoogle } from "../services/authService";
import "./GoogleCallback.css";

function GoogleCallback() {
  const [status, setStatus] = useState("processing"); // processing | error
  const [error, setError] = useState(null);
  const navigate = useNavigate();
  const location = useLocation();
  const processedRef = useRef(false);

  const processAuth = useCallback(async () => {
    setStatus("processing");
    setError(null);

    const params = new URLSearchParams(location.search);
    const code = params.get("code");

    if (!code) {
      setStatus("error");
      setError("No se recibió el código de autorización de Google.");
      return;
    }

    try {
      const data = await registerWithGoogle(code);

      // Guardar token en localStorage
      localStorage.setItem("token", data.token);
      localStorage.setItem(
        "user",
        JSON.stringify({
          id: data.userId,
          email: data.email,
          name: data.name,
          picture: data.pictureUrl,
        }),
      );

      // Redirigir según el estado del usuario
      if (data.isNewUser) {
        navigate("/completar-perfil");
      } else {
        navigate("/home");
      }
    } catch (err) {
      console.error("Auth error:", err);
      setStatus("error");
      setError(
        err.message || "Error al conectar con el servidor. Inténtalo de nuevo.",
      );
    }
  }, [location.search, navigate]);

  /* eslint-disable react-hooks/exhaustive-deps */
  useEffect(() => {
    // Evitar doble ejecución en StrictMode de React.
    // processAuth está en useCallback con sus propias dependencias;
    // agregar más deps aquí causaría re-ejecuciones no deseadas.
    if (processedRef.current) return;
    processedRef.current = true;
    processAuth();
  }, [processAuth]);
  /* eslint-enable react-hooks/exhaustive-deps */

  return (
    <div className="callback-container">
      <div className="callback-card">
        {status === "processing" && (
          <div className="callback-loading">
            <div className="spinner"></div>
            <h2>Autenticando...</h2>
            <p>Estamos procesando tu inicio de sesión con Google.</p>
          </div>
        )}

        {status === "error" && (
          <div className="callback-error">
            <div className="error-icon">!</div>
            <h2>Algo salió mal</h2>
            <p className="error-message">{error}</p>
            <div className="error-actions">
              <button
                className="btn-retry"
                onClick={() => {
                  processedRef.current = false;
                  processAuth();
                }}
              >
                Reintentar
              </button>
              <button
                className="btn-back"
                onClick={() => navigate("/registro")}
              >
                Volver al registro
              </button>
            </div>
          </div>
        )}
      </div>
    </div>
  );
}

export default GoogleCallback;
