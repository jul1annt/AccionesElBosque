/**
 * RegisterPage.jsx
 * Capa de presentación — Módulo Auth
 * Página de registro. Inicia el flujo OAuth redirigiendo al usuario a Google.
 */

import GoogleAuthButton from "../components/GoogleAuthButton";
import "./RegisterPage.css";

const GOOGLE_CLIENT_ID = import.meta.env.VITE_GOOGLE_CLIENT_ID;

/**
 * Construye la URL de autorización de Google OAuth 2.0.
 * REDIRECT_URI se resuelve en tiempo de ejecución para garantizar que
 * window.location.origin esté disponible en el navegador.
 * @returns {string} URL completa de autorización.
 */
function buildGoogleOAuthURL() {
  const redirectUri =
    import.meta.env.VITE_GOOGLE_REDIRECT_URI ||
    `${window.location.origin}/auth/google/callback`;

  const params = new URLSearchParams({
    client_id: GOOGLE_CLIENT_ID,
    redirect_uri: redirectUri,
    response_type: "code",
    scope: "openid email profile",
    access_type: "offline",
    prompt: "select_account",
  });
  return `https://accounts.google.com/o/oauth2/v2/auth?${params.toString()}`;
}

/**
 * Inicia el flujo OAuth redirigiendo al usuario a Google.
 */
function handleGoogleRegister() {
  const authUrl = buildGoogleOAuthURL();
  window.location.href = authUrl;
}

function RegisterPage() {
  return (
    <div className="register-page">
      {/* Fondo con partículas animadas */}
      <div className="register-page__bg" aria-hidden="true">
        <div className="bg-orb bg-orb--1" />
        <div className="bg-orb bg-orb--2" />
        <div className="bg-orb bg-orb--3" />
      </div>

      <main className="register-page__card" role="main">
        {/* Logo / Identidad */}
        <div className="register-page__brand">
          <div className="brand-icon" aria-hidden="true">
            <svg
              viewBox="0 0 48 48"
              fill="none"
              xmlns="http://www.w3.org/2000/svg"
            >
              <path
                d="M24 4L44 38H4L24 4Z"
                fill="url(#tree-gradient)"
                opacity="0.9"
              />
              <rect
                x="21"
                y="38"
                width="6"
                height="8"
                rx="2"
                fill="url(#tree-gradient)"
              />
              <defs>
                <linearGradient
                  id="tree-gradient"
                  x1="24"
                  y1="4"
                  x2="24"
                  y2="46"
                  gradientUnits="userSpaceOnUse"
                >
                  <stop offset="0%" stopColor="#4ade80" />
                  <stop offset="100%" stopColor="#16a34a" />
                </linearGradient>
              </defs>
            </svg>
          </div>
          <h1 className="brand-name">Acciones El Bosque</h1>
          <p className="brand-tagline">Plataforma de inversión inteligente</p>
        </div>

        {/* Separador */}
        <div className="register-page__divider" />

        {/* Contenido del registro */}
        <div className="register-page__content">
          <h2 className="register-page__title">Crea tu cuenta</h2>
          <p className="register-page__subtitle">
            Únete a miles de inversionistas. Accede de forma segura con tu
            cuenta de Google.
          </p>

          <div className="register-page__action">
            <GoogleAuthButton
              onClick={handleGoogleRegister}
              label="Registrarse con Google"
            />
          </div>

          <p className="register-page__legal">
            Al continuar, aceptas nuestros{" "}
            <a href="/terminos" className="register-page__link">
              Términos de servicio
            </a>{" "}
            y{" "}
            <a href="/privacidad" className="register-page__link">
              Política de privacidad
            </a>
            .
          </p>
        </div>

        {/* Footer de la card */}
        <div className="register-page__footer">
          <p>
            ¿Ya tienes cuenta?{" "}
            <a
              href="/login"
              className="register-page__link register-page__link--accent"
            >
              Inicia sesión
            </a>
          </p>
        </div>
      </main>
    </div>
  );
}

export default RegisterPage;
