package co.edu.unbosque.accioneselbosque.notificaciones.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final JavaMailSender mailSender;

    @Value("${app.mail.from}")
    private String from;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Async
    public void enviarBienvenida(String destinatario, String nombre) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setFrom(from);
            helper.setTo(destinatario);
            helper.setSubject("¡Bienvenido a AccionesElBosque!");
            helper.setText(construirCuerpo(nombre), true);

            mailSender.send(message);

        } catch (MessagingException e) {
            // Log del error pero no interrumpimos el flujo de registro
            System.err.println("Error enviando email de bienvenida a " + destinatario + ": " + e.getMessage());
        }
    }

    private String construirCuerpo(String nombre) {
        return """
                <html>
                  <body style="margin: 0; padding: 0; background-color: #f4f7f5; font-family: Arial, sans-serif; color: #333333;">

                    <table width="100%%" cellpadding="0" cellspacing="0" border="0" style="background-color: #f4f7f5; padding: 40px 0;">
                      <tr>
                        <td align="center">

                          <table width="600" cellpadding="0" cellspacing="0" border="0"
                                 style="background-color: #ffffff; border-radius: 12px; overflow: hidden; box-shadow: 0 4px 12px rgba(0,0,0,0.08);">

                            <!-- Header -->
                            <tr>
                              <td align="center"
                                  style="background: linear-gradient(135deg, #006b3f, #00995d); padding: 35px 20px;">
                                <h1 style="margin: 0; color: #ffffff; font-size: 32px; font-weight: bold;">
                                  Acciones El Bosque
                                </h1>
                                <p style="margin: 10px 0 0; color: #dff5e8; font-size: 16px;">
                                  Plataforma de Day Trading
                                </p>
                              </td>
                            </tr>

                            <!-- Content -->
                            <tr>
                              <td style="padding: 40px 35px;">

                                <h2 style="margin-top: 0; color: #006b3f; font-size: 26px;">
                                  ¡Hola, %s!
                                </h2>

                                <p style="font-size: 16px; line-height: 1.7; margin-bottom: 20px;">
                                  Te damos la bienvenida a
                                  <strong>Acciones El Bosque</strong>.
                                </p>

                                <p style="font-size: 16px; line-height: 1.7; margin-bottom: 20px;">
                                  Tu cuenta ha sido creada exitosamente y ya puedes acceder a todas las herramientas y funcionalidades de la plataforma.
                                </p>

                                <p style="font-size: 16px; line-height: 1.7; margin-bottom: 35px;">
                                  Haz clic en el siguiente botón para ingresar:
                                </p>

                                <!-- Button -->
                                <table cellpadding="0" cellspacing="0" border="0" align="center">
                                  <tr>
                                    <td align="center" bgcolor="#006b3f" style="border-radius: 8px;">
                                      <a href="http://localhost:5173"
                                         style="display: inline-block; padding: 14px 30px; font-size: 16px; color: #ffffff; text-decoration: none; font-weight: bold;">
                                        Ir a la plataforma
                                      </a>
                                    </td>
                                  </tr>
                                </table>

                                <p style="margin-top: 40px; font-size: 15px; line-height: 1.7; color: #555555;">
                                  Gracias por formar parte de nuestra comunidad.
                                </p>

                                <p style="font-size: 15px; color: #006b3f; font-weight: bold;">
                                  Equipo Acciones El Bosque
                                </p>

                              </td>
                            </tr>

                            <!-- Footer -->
                            <tr>
                              <td align="center"
                                  style="background-color: #f0f4f1; padding: 20px; border-top: 1px solid #d9e5dd;">

                                <p style="margin: 0; font-size: 13px; color: #777777;">
                                  PraiseTheCode
                                </p>

                                <p style="margin: 5px 0 0; font-size: 12px; color: #999999;">
                                  © 2026 Acciones El Bosque. Todos los derechos reservados.
                                </p>

                              </td>
                            </tr>

                          </table>

                        </td>
                      </tr>
                    </table>

                  </body>
                </html>
                """
                .formatted(nombre);
    }
}