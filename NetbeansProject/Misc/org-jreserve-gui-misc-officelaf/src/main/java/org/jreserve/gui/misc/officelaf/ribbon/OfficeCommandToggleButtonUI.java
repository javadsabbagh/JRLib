package org.jreserve.gui.misc.officelaf.ribbon;

import org.pushingpixels.flamingo.internal.ui.common.BasicCommandToggleButtonUI;
import org.jreserve.gui.misc.officelaf.util.CommandButtonPainter;

import javax.swing.*;
import javax.swing.plaf.BorderUIResource;
import javax.swing.plaf.ComponentUI;
import java.awt.*;

public class OfficeCommandToggleButtonUI extends BasicCommandToggleButtonUI {
    protected CommandButtonPainter painter;

    public static ComponentUI createUI(JComponent c) {
        return new OfficeCommandToggleButtonUI();
    }

    protected void installDefaults() {
        super.installDefaults();
        painter = new CommandButtonPainter(this.commandButton);
        commandButton.setBorder(new BorderUIResource.EmptyBorderUIResource(3, 3, 3, 3));
    }

    @Override
    protected void paintButtonBackground(Graphics graphics, Rectangle toFill) {
        painter.paintBackground(graphics, toFill);
    }

    @Override
    protected Color getForegroundColor(boolean isTextPaintedEnabled) {
        return isTextPaintedEnabled ? CommandButtonPainter.TEXT_COLOR : CommandButtonPainter.DISABLED_TEXT_COLOR;
    }
}
