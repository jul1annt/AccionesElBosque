/**
 * authService.js
 * Capa de servicio — Módulo Auth
 * Encapsula la comunicación con el backend para autenticación con Google OAuth.
 *
 * En desarrollo: el proxy de Vite (vite.config.js) intercepta /api/*
 * y lo redirige a http://localhost:8080, eliminando problemas de CORS.
 * En producción: VITE_API_URL debe apuntar al servidor real.
 */

const API_BASE = import.meta.env.PROD ? import.meta.env.VITE_API_URL || "" : ""; // En dev, usa ruta relativa — el proxy de Vite la reenvía al backend

/**
 * Registra o autentica al usuario mediante el authorization code de Google.
 * @param {string} code - El authorization code devuelto por Google OAuth.
 * @returns {Promise<{token: string, userId: string, email: string, name: string, pictureUrl: string, isNewUser: boolean}>}
 * @throws {Error} Si el servidor responde con error o no hay conexión.
 */
export async function registerWithGoogle(code) {
  const response = await fetch(`${API_BASE}/api/auth/google/register`, {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify({ code }),
  });

  if (!response.ok) {
    let errorMessage = `Error del servidor: ${response.status}`;
    try {
      const errorData = await response.json();
      errorMessage = errorData.message || errorMessage;
    } catch {
      // El body no es JSON, usamos el mensaje por defecto
    }
    throw new Error(errorMessage);
  }

  return response.json();
}
