/* ******************************************************************************
 * Copyright (c) 2014 - 2016 Fabian Prasser.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Fabian Prasser - initial API and implementation
 * ****************************************************************************
 */

package de.linearbits.preferences;

import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;

/**
 * Editor for integer variables
 * @author Fabian Prasser
 */
class EditorInteger extends Editor<Integer> {

    /** Widget*/
    private Text text;

    /**
     * Constructor
     * @param dialog
     */
    public EditorInteger(PreferencesDialog dialog, Integer _default) {
        super(dialog, null, _default);
    }

    /**
     * Constructor
     * @param dialog
     * @param validator
     */
    public EditorInteger(PreferencesDialog dialog, Validator<Integer> validator, Integer _default) {
        super(dialog, validator, _default);
    }

    @Override
    void createControl(final Composite parent) {

        final GridData ldata = GridDataFactory.swtDefaults().grab(true, false).indent(0, 0).align(SWT.FILL, SWT.FILL).create();
        text = new Text(parent, SWT.SINGLE | SWT.BORDER);
        ldata.minimumWidth = getDialog().getConfiguration().getMinimalTextWidth();
        text.setText("");
        text.setLayoutData(ldata);

        final Color black = new Color(text.getDisplay(), 0, 0, 0);
        final Color red = new Color(text.getDisplay(), 255, 0, 0);

        text.addModifyListener(new ModifyListener() {
            @Override
            public void modifyText(final ModifyEvent arg0) {
                if (accepts(text.getText())) {
                    text.setForeground(black);
                    setValid(true);
                    update();
                } else {
                    text.setForeground(red);
                    setValid(false);
                    update();
                }
            }
        });

        text.addDisposeListener(new DisposeListener() {
            @Override
            public void widgetDisposed(DisposeEvent arg0) {
                black.dispose();
                red.dispose();
            }
        });
        
        super.createUndoButton(parent);
        super.createDefaultButton(parent);
        super.update();
    }

    @Override
    String format(Integer d) {
        return d.toString();
    }

    @Override
    Integer getValue() {
        return parse(text.getText());
    }

    @Override
    Integer parse(final String s) {
        return Integer.valueOf(s);
    }

    @Override
    void setValue(Object t) {
        this.setInitialValue((Integer) t);
        this.text.setText(t.toString());
        super.update();
    }
}
