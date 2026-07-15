package arbolE;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.JPanel;
/**
 *
 * @author marie
 * =============================================
 * MÉTODO PARA DIBUJAR ÁRBOL GRÁFICO
 * INSTRUCCIONES:
 * a. Solicitar el ancho y color de las líneas
 * b. Solicitar el ancho y color de los nodos
 * c. Decorarlo con contenido del nodo
 * d. Agregar el método ON CLOSE con la opción de this.dispose() para EVITAR
 *    que cierre el proyecto.
 * e. Al lado de cada nodo, dibujar un recuadro adicional con el valor
 *    evaluado del token (constante, variable o resultado de operación).
 *
 * NOMBRE: Ibarra Hernandez Jeanely Fernanda
 * FECHA: 08 de Julio del 2026
 * ==============================================
 */
public class PanelArbol extends JPanel {
    private final Nodo raiz;
    private final Color colorNodo;
    private final Color colorLinea;
    private final int anchoNodo;
    private final int anchoLinea;
    private final int ESPACIO_VERTICAL = 70;
    private final int ESPACIO_MINIMO = 70;

    public PanelArbol(Nodo raiz, Color colorNodo, Color colorLinea,
            int anchoNodo, int anchoLinea) {
        this.raiz = raiz;
        this.colorNodo = colorNodo;
        this.colorLinea = colorLinea;
        this.anchoNodo = anchoNodo;
        this.anchoLinea = anchoLinea;
        setBackground(Color.WHITE);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (raiz == null) {
            return;
        }
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setStroke(new BasicStroke(anchoLinea));
        dibujarNodo(g2, raiz, getWidth() / 2, 40, getWidth() / 4);
    }

    private void dibujarNodo(Graphics2D g, Nodo nodo,
            int x, int y, int espacioHorizontal) {
        if (nodo == null) {
            return;
        }
        int espacioReal = Math.max(espacioHorizontal, ESPACIO_MINIMO);

        // LINEAS
        g.setColor(colorLinea);
        g.setStroke(new BasicStroke(anchoLinea));
        if (nodo.getIzq() != null) {
            g.drawLine(
                    x,
                    y,
                    x - espacioReal,
                    y + ESPACIO_VERTICAL);
            dibujarNodo(
                    g,
                    nodo.getIzq(),
                    x - espacioReal,
                    y + ESPACIO_VERTICAL,
                    espacioReal / 2);
        }
        if (nodo.getDer() != null) {
            g.drawLine(
                    x,
                    y,
                    x + espacioReal,
                    y + ESPACIO_VERTICAL);
            dibujarNodo(
                    g,
                    nodo.getDer(),
                    x + espacioReal,
                    y + ESPACIO_VERTICAL,
                    espacioReal / 2);
        }

        FontMetrics fm = g.getFontMetrics();
        String dato = nodo.getDato() != null ? nodo.getDato() : "";
        String valorTexto = formatearValor(nodo.getValor());

        // Fuente en negritas para el texto de las cajas
        Font fuenteNegrita = new Font("Arial", Font.BOLD, (anchoNodo/2 - 2));
        g.setFont(fuenteNegrita);
        fm = g.getFontMetrics(fuenteNegrita);

        int anchoTextoDato = fm.stringWidth(dato);
        int anchoCajaDato = Math.max(anchoNodo, anchoTextoDato + 10);

        int anchoTextoValor = fm.stringWidth(valorTexto);
        int anchoCajaValor = Math.max(anchoNodo, anchoTextoValor + 10);

        int altoTexto = fm.getAscent();

        int anchoTotal = anchoCajaDato + anchoCajaValor;
        int xDato = x - anchoTotal / 2;

        // TOKEN (dato)
        g.setColor(colorNodo);
        g.fillRect(xDato, y - anchoNodo / 2, anchoCajaDato, anchoNodo);
        g.setColor(colorLinea);
        g.setStroke(new BasicStroke(anchoLinea));
        g.drawRect(xDato, y - anchoNodo / 2, anchoCajaDato, anchoNodo);

        g.setColor(colorLinea); // texto en negro
        g.drawString(dato, xDato + (anchoCajaDato - anchoTextoDato) / 2, y + altoTexto / 4);

        //RECUADRO DEL VALOR
        int xValor = xDato + anchoCajaDato;
        g.setColor(colorNodo);
        g.fillRect(xValor, y - anchoNodo / 2, anchoCajaValor, anchoNodo);
        g.setColor(colorLinea);
        g.drawRect(xValor, y - anchoNodo / 2, anchoCajaValor, anchoNodo);

        g.setColor(colorLinea); // texto en negro
        g.drawString(valorTexto, xValor + (anchoCajaValor - anchoTextoValor) / 2, y + altoTexto / 4);
    }//dibujarNodo

    private String formatearValor(double valor) {
        if (valor == Math.floor(valor) && !Double.isInfinite(valor)) {
            return String.valueOf((long) valor);
        }
        return String.valueOf(valor);
    }
}//FIN CLASE