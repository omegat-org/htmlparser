// HTMLParser Library v1_3_20021228_20021207 - A java-based parser for HTML
// Copyright (C) Dec 31, 2000 Somik Raha
//
// This library is free software; you can redistribute it and/or
// modify it under the terms of the GNU Lesser General Public
// License as published by the Free Software Foundation; either
// version 2.1 of the License, or (at your option) any later version.
//
// This library is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
// Lesser General Public License for more details.
// 
// You should have received a copy of the GNU Lesser General Public
// License along with this library; if not, write to the Free Software
// Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
//
// For any questions or suggestions, you can write to me at :
// Email :somik@industriallogic.com
// 
// Postal Address : 
// Somik Raha
// Extreme Programmer & Coach
// Industrial Logic Corporation
// 2583 Cedar Street, Berkeley, 
// CA 94708, USA
// Website : http://www.industriallogic.com

package org.htmlparser.beans;

import java.beans.*;

/**
 * Meta information about the HTMLTextBean.
 */
public class HTMLTextBeanBeanInfo extends SimpleBeanInfo
{
    
    // Bean descriptor //GEN-FIRST:BeanDescriptor
    /*lazy BeanDescriptor*/
    private static BeanDescriptor getBdescriptor(){
        BeanDescriptor beanDescriptor = new BeanDescriptor  ( HTMLTextBean.class , null );//GEN-HEADEREND:BeanDescriptor
        
        // Here you can add code for customizing the BeanDescriptor.
        
        return beanDescriptor;         }//GEN-LAST:BeanDescriptor
    
    
    // Property identifiers //GEN-FIRST:Properties
    private static final int PROPERTY_verifyInputWhenFocusTarget = 0;
    private static final int PROPERTY_componentOrientation = 1;
    private static final int PROPERTY_fontSet = 2;
    private static final int PROPERTY_locationOnScreen = 3;
    private static final int PROPERTY_mouseWheelListeners = 4;
    private static final int PROPERTY_colorModel = 5;
    private static final int PROPERTY_columns = 6;
    private static final int PROPERTY_focusTraversalPolicy = 7;
    private static final int PROPERTY_links = 8;
    private static final int PROPERTY_strings = 9;
    private static final int PROPERTY_URL = 10;
    private static final int PROPERTY_caretColor = 11;
    private static final int PROPERTY_registeredKeyStrokes = 12;
    private static final int PROPERTY_scrollableTracksViewportWidth = 13;
    private static final int PROPERTY_alignmentX = 14;
    private static final int PROPERTY_paintingTile = 15;
    private static final int PROPERTY_alignmentY = 16;
    private static final int PROPERTY_hierarchyListeners = 17;
    private static final int PROPERTY_accessibleContext = 18;
    private static final int PROPERTY_preferredSize = 19;
    private static final int PROPERTY_actions = 20;
    private static final int PROPERTY_tabSize = 21;
    private static final int PROPERTY_managingFocus = 22;
    private static final int PROPERTY_minimumSizeSet = 23;
    private static final int PROPERTY_scrollableTracksViewportHeight = 24;
    private static final int PROPERTY_focusAccelerator = 25;
    private static final int PROPERTY_focusTraversalPolicySet = 26;
    private static final int PROPERTY_y = 27;
    private static final int PROPERTY_x = 28;
    private static final int PROPERTY_cursorSet = 29;
    private static final int PROPERTY_inputMethodRequests = 30;
    private static final int PROPERTY_containerListeners = 31;
    private static final int PROPERTY_insets = 32;
    private static final int PROPERTY_componentCount = 33;
    private static final int PROPERTY_components = 34;
    private static final int PROPERTY_inputVerifier = 35;
    private static final int PROPERTY_hierarchyBoundsListeners = 36;
    private static final int PROPERTY_border = 37;
    private static final int PROPERTY_name = 38;
    private static final int PROPERTY_optimizedDrawingEnabled = 39;
    private static final int PROPERTY_graphics = 40;
    private static final int PROPERTY_caretListeners = 41;
    private static final int PROPERTY_toolTipText = 42;
    private static final int PROPERTY_minimumSize = 43;
    private static final int PROPERTY_focusTraversalKeysEnabled = 44;
    private static final int PROPERTY_disabledTextColor = 45;
    private static final int PROPERTY_lineWrap = 46;
    private static final int PROPERTY_foreground = 47;
    private static final int PROPERTY_selectionStart = 48;
    private static final int PROPERTY_navigationFilter = 49;
    private static final int PROPERTY_ignoreRepaint = 50;
    private static final int PROPERTY_focusable = 51;
    private static final int PROPERTY_preferredSizeSet = 52;
    private static final int PROPERTY_visible = 53;
    private static final int PROPERTY_focusCycleRootAncestor = 54;
    private static final int PROPERTY_margin = 55;
    private static final int PROPERTY_parent = 56;
    private static final int PROPERTY_rootPane = 57;
    private static final int PROPERTY_editable = 58;
    private static final int PROPERTY_lightweight = 59;
    private static final int PROPERTY_dragEnabled = 60;
    private static final int PROPERTY_width = 61;
    private static final int PROPERTY_keyListeners = 62;
    private static final int PROPERTY_toolkit = 63;
    private static final int PROPERTY_inputContext = 64;
    private static final int PROPERTY_layout = 65;
    private static final int PROPERTY_opaque = 66;
    private static final int PROPERTY_font = 67;
    private static final int PROPERTY_locale = 68;
    private static final int PROPERTY_cursor = 69;
    private static final int PROPERTY_keymap = 70;
    private static final int PROPERTY_selectedText = 71;
    private static final int PROPERTY_inputMethodListeners = 72;
    private static final int PROPERTY_transferHandler = 73;
    private static final int PROPERTY_vetoableChangeListeners = 74;
    private static final int PROPERTY_caret = 75;
    private static final int PROPERTY_doubleBuffered = 76;
    private static final int PROPERTY_visibleRect = 77;
    private static final int PROPERTY_maximumSizeSet = 78;
    private static final int PROPERTY_rows = 79;
    private static final int PROPERTY_valid = 80;
    private static final int PROPERTY_focusCycleRoot = 81;
    private static final int PROPERTY_maximumSize = 82;
    private static final int PROPERTY_mouseMotionListeners = 83;
    private static final int PROPERTY_bounds = 84;
    private static final int PROPERTY_treeLock = 85;
    private static final int PROPERTY_text = 86;
    private static final int PROPERTY_focusTraversable = 87;
    private static final int PROPERTY_propertyChangeListeners = 88;
    private static final int PROPERTY_autoscrolls = 89;
    private static final int PROPERTY_componentListeners = 90;
    private static final int PROPERTY_showing = 91;
    private static final int PROPERTY_dropTarget = 92;
    private static final int PROPERTY_focusListeners = 93;
    private static final int PROPERTY_nextFocusableComponent = 94;
    private static final int PROPERTY_caretPosition = 95;
    private static final int PROPERTY_peer = 96;
    private static final int PROPERTY_height = 97;
    private static final int PROPERTY_topLevelAncestor = 98;
    private static final int PROPERTY_document = 99;
    private static final int PROPERTY_displayable = 100;
    private static final int PROPERTY_background = 101;
    private static final int PROPERTY_selectedTextColor = 102;
    private static final int PROPERTY_selectionColor = 103;
    private static final int PROPERTY_graphicsConfiguration = 104;
    private static final int PROPERTY_selectionEnd = 105;
    private static final int PROPERTY_focusOwner = 106;
    private static final int PROPERTY_ancestorListeners = 107;
    private static final int PROPERTY_requestFocusEnabled = 108;
    private static final int PROPERTY_debugGraphicsOptions = 109;
    private static final int PROPERTY_wrapStyleWord = 110;
    private static final int PROPERTY_backgroundSet = 111;
    private static final int PROPERTY_actionMap = 112;
    private static final int PROPERTY_mouseListeners = 113;
    private static final int PROPERTY_enabled = 114;
    private static final int PROPERTY_foregroundSet = 115;
    private static final int PROPERTY_highlighter = 116;
    private static final int PROPERTY_lineCount = 117;
    private static final int PROPERTY_validateRoot = 118;
    private static final int PROPERTY_UI = 119;
    private static final int PROPERTY_preferredScrollableViewportSize = 120;
    private static final int PROPERTY_UIClassID = 121;
    private static final int PROPERTY_lineOfOffset = 122;
    private static final int PROPERTY_component = 123;
    private static final int PROPERTY_lineEndOffset = 124;
    private static final int PROPERTY_lineStartOffset = 125;
    private static final int PROPERTY_focusTraversalKeys = 126;

    // Property array 
    /*lazy PropertyDescriptor*/
    private static PropertyDescriptor[] getPdescriptor(){
        PropertyDescriptor[] properties = new PropertyDescriptor[127];
    
        try {
            properties[PROPERTY_verifyInputWhenFocusTarget] = new PropertyDescriptor ( "verifyInputWhenFocusTarget", HTMLTextBean.class, "getVerifyInputWhenFocusTarget", "setVerifyInputWhenFocusTarget" );
            properties[PROPERTY_componentOrientation] = new PropertyDescriptor ( "componentOrientation", HTMLTextBean.class, "getComponentOrientation", "setComponentOrientation" );
            properties[PROPERTY_fontSet] = new PropertyDescriptor ( "fontSet", HTMLTextBean.class, "isFontSet", null );
            properties[PROPERTY_locationOnScreen] = new PropertyDescriptor ( "locationOnScreen", HTMLTextBean.class, "getLocationOnScreen", null );
            properties[PROPERTY_mouseWheelListeners] = new PropertyDescriptor ( "mouseWheelListeners", HTMLTextBean.class, "getMouseWheelListeners", null );
            properties[PROPERTY_colorModel] = new PropertyDescriptor ( "colorModel", HTMLTextBean.class, "getColorModel", null );
            properties[PROPERTY_columns] = new PropertyDescriptor ( "columns", HTMLTextBean.class, "getColumns", "setColumns" );
            properties[PROPERTY_focusTraversalPolicy] = new PropertyDescriptor ( "focusTraversalPolicy", HTMLTextBean.class, "getFocusTraversalPolicy", "setFocusTraversalPolicy" );
            properties[PROPERTY_links] = new PropertyDescriptor ( "links", HTMLTextBean.class, "getLinks", "setLinks" );
            properties[PROPERTY_strings] = new PropertyDescriptor ( "strings", HTMLTextBean.class, "getStrings", null );
            properties[PROPERTY_URL] = new PropertyDescriptor ( "URL", HTMLTextBean.class, "getURL", "setURL" );
            properties[PROPERTY_caretColor] = new PropertyDescriptor ( "caretColor", HTMLTextBean.class, "getCaretColor", "setCaretColor" );
            properties[PROPERTY_registeredKeyStrokes] = new PropertyDescriptor ( "registeredKeyStrokes", HTMLTextBean.class, "getRegisteredKeyStrokes", null );
            properties[PROPERTY_scrollableTracksViewportWidth] = new PropertyDescriptor ( "scrollableTracksViewportWidth", HTMLTextBean.class, "getScrollableTracksViewportWidth", null );
            properties[PROPERTY_alignmentX] = new PropertyDescriptor ( "alignmentX", HTMLTextBean.class, "getAlignmentX", "setAlignmentX" );
            properties[PROPERTY_paintingTile] = new PropertyDescriptor ( "paintingTile", HTMLTextBean.class, "isPaintingTile", null );
            properties[PROPERTY_alignmentY] = new PropertyDescriptor ( "alignmentY", HTMLTextBean.class, "getAlignmentY", "setAlignmentY" );
            properties[PROPERTY_hierarchyListeners] = new PropertyDescriptor ( "hierarchyListeners", HTMLTextBean.class, "getHierarchyListeners", null );
            properties[PROPERTY_accessibleContext] = new PropertyDescriptor ( "accessibleContext", HTMLTextBean.class, "getAccessibleContext", null );
            properties[PROPERTY_preferredSize] = new PropertyDescriptor ( "preferredSize", HTMLTextBean.class, "getPreferredSize", "setPreferredSize" );
            properties[PROPERTY_actions] = new PropertyDescriptor ( "actions", HTMLTextBean.class, "getActions", null );
            properties[PROPERTY_tabSize] = new PropertyDescriptor ( "tabSize", HTMLTextBean.class, "getTabSize", "setTabSize" );
            properties[PROPERTY_managingFocus] = new PropertyDescriptor ( "managingFocus", HTMLTextBean.class, "isManagingFocus", null );
            properties[PROPERTY_minimumSizeSet] = new PropertyDescriptor ( "minimumSizeSet", HTMLTextBean.class, "isMinimumSizeSet", null );
            properties[PROPERTY_scrollableTracksViewportHeight] = new PropertyDescriptor ( "scrollableTracksViewportHeight", HTMLTextBean.class, "getScrollableTracksViewportHeight", null );
            properties[PROPERTY_focusAccelerator] = new PropertyDescriptor ( "focusAccelerator", HTMLTextBean.class, "getFocusAccelerator", "setFocusAccelerator" );
            properties[PROPERTY_focusTraversalPolicySet] = new PropertyDescriptor ( "focusTraversalPolicySet", HTMLTextBean.class, "isFocusTraversalPolicySet", null );
            properties[PROPERTY_y] = new PropertyDescriptor ( "y", HTMLTextBean.class, "getY", null );
            properties[PROPERTY_x] = new PropertyDescriptor ( "x", HTMLTextBean.class, "getX", null );
            properties[PROPERTY_cursorSet] = new PropertyDescriptor ( "cursorSet", HTMLTextBean.class, "isCursorSet", null );
            properties[PROPERTY_inputMethodRequests] = new PropertyDescriptor ( "inputMethodRequests", HTMLTextBean.class, "getInputMethodRequests", null );
            properties[PROPERTY_containerListeners] = new PropertyDescriptor ( "containerListeners", HTMLTextBean.class, "getContainerListeners", null );
            properties[PROPERTY_insets] = new PropertyDescriptor ( "insets", HTMLTextBean.class, "getInsets", null );
            properties[PROPERTY_componentCount] = new PropertyDescriptor ( "componentCount", HTMLTextBean.class, "getComponentCount", null );
            properties[PROPERTY_components] = new PropertyDescriptor ( "components", HTMLTextBean.class, "getComponents", null );
            properties[PROPERTY_inputVerifier] = new PropertyDescriptor ( "inputVerifier", HTMLTextBean.class, "getInputVerifier", "setInputVerifier" );
            properties[PROPERTY_hierarchyBoundsListeners] = new PropertyDescriptor ( "hierarchyBoundsListeners", HTMLTextBean.class, "getHierarchyBoundsListeners", null );
            properties[PROPERTY_border] = new PropertyDescriptor ( "border", HTMLTextBean.class, "getBorder", "setBorder" );
            properties[PROPERTY_name] = new PropertyDescriptor ( "name", HTMLTextBean.class, "getName", "setName" );
            properties[PROPERTY_optimizedDrawingEnabled] = new PropertyDescriptor ( "optimizedDrawingEnabled", HTMLTextBean.class, "isOptimizedDrawingEnabled", null );
            properties[PROPERTY_graphics] = new PropertyDescriptor ( "graphics", HTMLTextBean.class, "getGraphics", null );
            properties[PROPERTY_caretListeners] = new PropertyDescriptor ( "caretListeners", HTMLTextBean.class, "getCaretListeners", null );
            properties[PROPERTY_toolTipText] = new PropertyDescriptor ( "toolTipText", HTMLTextBean.class, "getToolTipText", "setToolTipText" );
            properties[PROPERTY_minimumSize] = new PropertyDescriptor ( "minimumSize", HTMLTextBean.class, "getMinimumSize", "setMinimumSize" );
            properties[PROPERTY_focusTraversalKeysEnabled] = new PropertyDescriptor ( "focusTraversalKeysEnabled", HTMLTextBean.class, "getFocusTraversalKeysEnabled", "setFocusTraversalKeysEnabled" );
            properties[PROPERTY_disabledTextColor] = new PropertyDescriptor ( "disabledTextColor", HTMLTextBean.class, "getDisabledTextColor", "setDisabledTextColor" );
            properties[PROPERTY_lineWrap] = new PropertyDescriptor ( "lineWrap", HTMLTextBean.class, "getLineWrap", "setLineWrap" );
            properties[PROPERTY_foreground] = new PropertyDescriptor ( "foreground", HTMLTextBean.class, "getForeground", "setForeground" );
            properties[PROPERTY_selectionStart] = new PropertyDescriptor ( "selectionStart", HTMLTextBean.class, "getSelectionStart", "setSelectionStart" );
            properties[PROPERTY_navigationFilter] = new PropertyDescriptor ( "navigationFilter", HTMLTextBean.class, "getNavigationFilter", "setNavigationFilter" );
            properties[PROPERTY_ignoreRepaint] = new PropertyDescriptor ( "ignoreRepaint", HTMLTextBean.class, "getIgnoreRepaint", "setIgnoreRepaint" );
            properties[PROPERTY_focusable] = new PropertyDescriptor ( "focusable", HTMLTextBean.class, "isFocusable", "setFocusable" );
            properties[PROPERTY_preferredSizeSet] = new PropertyDescriptor ( "preferredSizeSet", HTMLTextBean.class, "isPreferredSizeSet", null );
            properties[PROPERTY_visible] = new PropertyDescriptor ( "visible", HTMLTextBean.class, "isVisible", "setVisible" );
            properties[PROPERTY_focusCycleRootAncestor] = new PropertyDescriptor ( "focusCycleRootAncestor", HTMLTextBean.class, "getFocusCycleRootAncestor", null );
            properties[PROPERTY_margin] = new PropertyDescriptor ( "margin", HTMLTextBean.class, "getMargin", "setMargin" );
            properties[PROPERTY_parent] = new PropertyDescriptor ( "parent", HTMLTextBean.class, "getParent", null );
            properties[PROPERTY_rootPane] = new PropertyDescriptor ( "rootPane", HTMLTextBean.class, "getRootPane", null );
            properties[PROPERTY_editable] = new PropertyDescriptor ( "editable", HTMLTextBean.class, "isEditable", "setEditable" );
            properties[PROPERTY_lightweight] = new PropertyDescriptor ( "lightweight", HTMLTextBean.class, "isLightweight", null );
            properties[PROPERTY_dragEnabled] = new PropertyDescriptor ( "dragEnabled", HTMLTextBean.class, "getDragEnabled", "setDragEnabled" );
            properties[PROPERTY_width] = new PropertyDescriptor ( "width", HTMLTextBean.class, "getWidth", null );
            properties[PROPERTY_keyListeners] = new PropertyDescriptor ( "keyListeners", HTMLTextBean.class, "getKeyListeners", null );
            properties[PROPERTY_toolkit] = new PropertyDescriptor ( "toolkit", HTMLTextBean.class, "getToolkit", null );
            properties[PROPERTY_inputContext] = new PropertyDescriptor ( "inputContext", HTMLTextBean.class, "getInputContext", null );
            properties[PROPERTY_layout] = new PropertyDescriptor ( "layout", HTMLTextBean.class, "getLayout", "setLayout" );
            properties[PROPERTY_opaque] = new PropertyDescriptor ( "opaque", HTMLTextBean.class, "isOpaque", "setOpaque" );
            properties[PROPERTY_font] = new PropertyDescriptor ( "font", HTMLTextBean.class, "getFont", "setFont" );
            properties[PROPERTY_locale] = new PropertyDescriptor ( "locale", HTMLTextBean.class, "getLocale", "setLocale" );
            properties[PROPERTY_cursor] = new PropertyDescriptor ( "cursor", HTMLTextBean.class, "getCursor", "setCursor" );
            properties[PROPERTY_keymap] = new PropertyDescriptor ( "keymap", HTMLTextBean.class, "getKeymap", "setKeymap" );
            properties[PROPERTY_selectedText] = new PropertyDescriptor ( "selectedText", HTMLTextBean.class, "getSelectedText", null );
            properties[PROPERTY_inputMethodListeners] = new PropertyDescriptor ( "inputMethodListeners", HTMLTextBean.class, "getInputMethodListeners", null );
            properties[PROPERTY_transferHandler] = new PropertyDescriptor ( "transferHandler", HTMLTextBean.class, "getTransferHandler", "setTransferHandler" );
            properties[PROPERTY_vetoableChangeListeners] = new PropertyDescriptor ( "vetoableChangeListeners", HTMLTextBean.class, "getVetoableChangeListeners", null );
            properties[PROPERTY_caret] = new PropertyDescriptor ( "caret", HTMLTextBean.class, "getCaret", "setCaret" );
            properties[PROPERTY_doubleBuffered] = new PropertyDescriptor ( "doubleBuffered", HTMLTextBean.class, "isDoubleBuffered", "setDoubleBuffered" );
            properties[PROPERTY_visibleRect] = new PropertyDescriptor ( "visibleRect", HTMLTextBean.class, "getVisibleRect", null );
            properties[PROPERTY_maximumSizeSet] = new PropertyDescriptor ( "maximumSizeSet", HTMLTextBean.class, "isMaximumSizeSet", null );
            properties[PROPERTY_rows] = new PropertyDescriptor ( "rows", HTMLTextBean.class, "getRows", "setRows" );
            properties[PROPERTY_valid] = new PropertyDescriptor ( "valid", HTMLTextBean.class, "isValid", null );
            properties[PROPERTY_focusCycleRoot] = new PropertyDescriptor ( "focusCycleRoot", HTMLTextBean.class, "isFocusCycleRoot", "setFocusCycleRoot" );
            properties[PROPERTY_maximumSize] = new PropertyDescriptor ( "maximumSize", HTMLTextBean.class, "getMaximumSize", "setMaximumSize" );
            properties[PROPERTY_mouseMotionListeners] = new PropertyDescriptor ( "mouseMotionListeners", HTMLTextBean.class, "getMouseMotionListeners", null );
            properties[PROPERTY_bounds] = new PropertyDescriptor ( "bounds", HTMLTextBean.class, "getBounds", "setBounds" );
            properties[PROPERTY_treeLock] = new PropertyDescriptor ( "treeLock", HTMLTextBean.class, "getTreeLock", null );
            properties[PROPERTY_text] = new PropertyDescriptor ( "text", HTMLTextBean.class, "getText", null );
            properties[PROPERTY_focusTraversable] = new PropertyDescriptor ( "focusTraversable", HTMLTextBean.class, "isFocusTraversable", null );
            properties[PROPERTY_propertyChangeListeners] = new PropertyDescriptor ( "propertyChangeListeners", HTMLTextBean.class, "getPropertyChangeListeners", null );
            properties[PROPERTY_autoscrolls] = new PropertyDescriptor ( "autoscrolls", HTMLTextBean.class, "getAutoscrolls", "setAutoscrolls" );
            properties[PROPERTY_componentListeners] = new PropertyDescriptor ( "componentListeners", HTMLTextBean.class, "getComponentListeners", null );
            properties[PROPERTY_showing] = new PropertyDescriptor ( "showing", HTMLTextBean.class, "isShowing", null );
            properties[PROPERTY_dropTarget] = new PropertyDescriptor ( "dropTarget", HTMLTextBean.class, "getDropTarget", "setDropTarget" );
            properties[PROPERTY_focusListeners] = new PropertyDescriptor ( "focusListeners", HTMLTextBean.class, "getFocusListeners", null );
            properties[PROPERTY_nextFocusableComponent] = new PropertyDescriptor ( "nextFocusableComponent", HTMLTextBean.class, "getNextFocusableComponent", "setNextFocusableComponent" );
            properties[PROPERTY_caretPosition] = new PropertyDescriptor ( "caretPosition", HTMLTextBean.class, "getCaretPosition", "setCaretPosition" );
            properties[PROPERTY_peer] = new PropertyDescriptor ( "peer", HTMLTextBean.class, "getPeer", null );
            properties[PROPERTY_height] = new PropertyDescriptor ( "height", HTMLTextBean.class, "getHeight", null );
            properties[PROPERTY_topLevelAncestor] = new PropertyDescriptor ( "topLevelAncestor", HTMLTextBean.class, "getTopLevelAncestor", null );
            properties[PROPERTY_document] = new PropertyDescriptor ( "document", HTMLTextBean.class, "getDocument", "setDocument" );
            properties[PROPERTY_displayable] = new PropertyDescriptor ( "displayable", HTMLTextBean.class, "isDisplayable", null );
            properties[PROPERTY_background] = new PropertyDescriptor ( "background", HTMLTextBean.class, "getBackground", "setBackground" );
            properties[PROPERTY_selectedTextColor] = new PropertyDescriptor ( "selectedTextColor", HTMLTextBean.class, "getSelectedTextColor", "setSelectedTextColor" );
            properties[PROPERTY_selectionColor] = new PropertyDescriptor ( "selectionColor", HTMLTextBean.class, "getSelectionColor", "setSelectionColor" );
            properties[PROPERTY_graphicsConfiguration] = new PropertyDescriptor ( "graphicsConfiguration", HTMLTextBean.class, "getGraphicsConfiguration", null );
            properties[PROPERTY_selectionEnd] = new PropertyDescriptor ( "selectionEnd", HTMLTextBean.class, "getSelectionEnd", "setSelectionEnd" );
            properties[PROPERTY_focusOwner] = new PropertyDescriptor ( "focusOwner", HTMLTextBean.class, "isFocusOwner", null );
            properties[PROPERTY_ancestorListeners] = new PropertyDescriptor ( "ancestorListeners", HTMLTextBean.class, "getAncestorListeners", null );
            properties[PROPERTY_requestFocusEnabled] = new PropertyDescriptor ( "requestFocusEnabled", HTMLTextBean.class, "isRequestFocusEnabled", "setRequestFocusEnabled" );
            properties[PROPERTY_debugGraphicsOptions] = new PropertyDescriptor ( "debugGraphicsOptions", HTMLTextBean.class, "getDebugGraphicsOptions", "setDebugGraphicsOptions" );
            properties[PROPERTY_wrapStyleWord] = new PropertyDescriptor ( "wrapStyleWord", HTMLTextBean.class, "getWrapStyleWord", "setWrapStyleWord" );
            properties[PROPERTY_backgroundSet] = new PropertyDescriptor ( "backgroundSet", HTMLTextBean.class, "isBackgroundSet", null );
            properties[PROPERTY_actionMap] = new PropertyDescriptor ( "actionMap", HTMLTextBean.class, "getActionMap", "setActionMap" );
            properties[PROPERTY_mouseListeners] = new PropertyDescriptor ( "mouseListeners", HTMLTextBean.class, "getMouseListeners", null );
            properties[PROPERTY_enabled] = new PropertyDescriptor ( "enabled", HTMLTextBean.class, "isEnabled", "setEnabled" );
            properties[PROPERTY_foregroundSet] = new PropertyDescriptor ( "foregroundSet", HTMLTextBean.class, "isForegroundSet", null );
            properties[PROPERTY_highlighter] = new PropertyDescriptor ( "highlighter", HTMLTextBean.class, "getHighlighter", "setHighlighter" );
            properties[PROPERTY_lineCount] = new PropertyDescriptor ( "lineCount", HTMLTextBean.class, "getLineCount", null );
            properties[PROPERTY_validateRoot] = new PropertyDescriptor ( "validateRoot", HTMLTextBean.class, "isValidateRoot", null );
            properties[PROPERTY_UI] = new PropertyDescriptor ( "UI", HTMLTextBean.class, "getUI", "setUI" );
            properties[PROPERTY_preferredScrollableViewportSize] = new PropertyDescriptor ( "preferredScrollableViewportSize", HTMLTextBean.class, "getPreferredScrollableViewportSize", null );
            properties[PROPERTY_UIClassID] = new PropertyDescriptor ( "UIClassID", HTMLTextBean.class, "getUIClassID", null );
            properties[PROPERTY_lineOfOffset] = new IndexedPropertyDescriptor ( "lineOfOffset", HTMLTextBean.class, null, null, "getLineOfOffset", null );
            properties[PROPERTY_component] = new IndexedPropertyDescriptor ( "component", HTMLTextBean.class, null, null, "getComponent", null );
            properties[PROPERTY_lineEndOffset] = new IndexedPropertyDescriptor ( "lineEndOffset", HTMLTextBean.class, null, null, "getLineEndOffset", null );
            properties[PROPERTY_lineStartOffset] = new IndexedPropertyDescriptor ( "lineStartOffset", HTMLTextBean.class, null, null, "getLineStartOffset", null );
            properties[PROPERTY_focusTraversalKeys] = new IndexedPropertyDescriptor ( "focusTraversalKeys", HTMLTextBean.class, null, null, "getFocusTraversalKeys", "setFocusTraversalKeys" );
        }
        catch( IntrospectionException e) {}//GEN-HEADEREND:Properties
        
        // Here you can add code for customizing the properties array.
        
        return properties;         }//GEN-LAST:Properties
    
    // EventSet identifiers//GEN-FIRST:Events
    private static final int EVENT_inputMethodListener = 0;
    private static final int EVENT_caretListener = 1;
    private static final int EVENT_containerListener = 2;
    private static final int EVENT_mouseMotionListener = 3;
    private static final int EVENT_mouseWheelListener = 4;
    private static final int EVENT_mouseListener = 5;
    private static final int EVENT_componentListener = 6;
    private static final int EVENT_hierarchyBoundsListener = 7;
    private static final int EVENT_ancestorListener = 8;
    private static final int EVENT_focusListener = 9;
    private static final int EVENT_keyListener = 10;
    private static final int EVENT_hierarchyListener = 11;
    private static final int EVENT_vetoableChangeListener = 12;
    private static final int EVENT_propertyChangeListener = 13;

    // EventSet array
    /*lazy EventSetDescriptor*/
    private static EventSetDescriptor[] getEdescriptor(){
        EventSetDescriptor[] eventSets = new EventSetDescriptor[14];
    
            try {
            eventSets[EVENT_inputMethodListener] = new EventSetDescriptor ( org.htmlparser.beans.HTMLTextBean.class, "inputMethodListener", java.awt.event.InputMethodListener.class, new String[] {"inputMethodTextChanged", "caretPositionChanged"}, "addInputMethodListener", "removeInputMethodListener" );
            eventSets[EVENT_caretListener] = new EventSetDescriptor ( org.htmlparser.beans.HTMLTextBean.class, "caretListener", javax.swing.event.CaretListener.class, new String[] {"caretUpdate"}, "addCaretListener", "removeCaretListener" );
            eventSets[EVENT_containerListener] = new EventSetDescriptor ( org.htmlparser.beans.HTMLTextBean.class, "containerListener", java.awt.event.ContainerListener.class, new String[] {"componentAdded", "componentRemoved"}, "addContainerListener", "removeContainerListener" );
            eventSets[EVENT_mouseMotionListener] = new EventSetDescriptor ( org.htmlparser.beans.HTMLTextBean.class, "mouseMotionListener", java.awt.event.MouseMotionListener.class, new String[] {"mouseDragged", "mouseMoved"}, "addMouseMotionListener", "removeMouseMotionListener" );
            eventSets[EVENT_mouseWheelListener] = new EventSetDescriptor ( org.htmlparser.beans.HTMLTextBean.class, "mouseWheelListener", java.awt.event.MouseWheelListener.class, new String[] {"mouseWheelMoved"}, "addMouseWheelListener", "removeMouseWheelListener" );
            eventSets[EVENT_mouseListener] = new EventSetDescriptor ( org.htmlparser.beans.HTMLTextBean.class, "mouseListener", java.awt.event.MouseListener.class, new String[] {"mouseClicked", "mousePressed", "mouseReleased", "mouseEntered", "mouseExited"}, "addMouseListener", "removeMouseListener" );
            eventSets[EVENT_componentListener] = new EventSetDescriptor ( org.htmlparser.beans.HTMLTextBean.class, "componentListener", java.awt.event.ComponentListener.class, new String[] {"componentResized", "componentMoved", "componentShown", "componentHidden"}, "addComponentListener", "removeComponentListener" );
            eventSets[EVENT_hierarchyBoundsListener] = new EventSetDescriptor ( org.htmlparser.beans.HTMLTextBean.class, "hierarchyBoundsListener", java.awt.event.HierarchyBoundsListener.class, new String[] {"ancestorMoved", "ancestorResized"}, "addHierarchyBoundsListener", "removeHierarchyBoundsListener" );
            eventSets[EVENT_ancestorListener] = new EventSetDescriptor ( org.htmlparser.beans.HTMLTextBean.class, "ancestorListener", javax.swing.event.AncestorListener.class, new String[] {"ancestorAdded", "ancestorRemoved", "ancestorMoved"}, "addAncestorListener", "removeAncestorListener" );
            eventSets[EVENT_focusListener] = new EventSetDescriptor ( org.htmlparser.beans.HTMLTextBean.class, "focusListener", java.awt.event.FocusListener.class, new String[] {"focusGained", "focusLost"}, "addFocusListener", "removeFocusListener" );
            eventSets[EVENT_keyListener] = new EventSetDescriptor ( org.htmlparser.beans.HTMLTextBean.class, "keyListener", java.awt.event.KeyListener.class, new String[] {"keyTyped", "keyPressed", "keyReleased"}, "addKeyListener", "removeKeyListener" );
            eventSets[EVENT_hierarchyListener] = new EventSetDescriptor ( org.htmlparser.beans.HTMLTextBean.class, "hierarchyListener", java.awt.event.HierarchyListener.class, new String[] {"hierarchyChanged"}, "addHierarchyListener", "removeHierarchyListener" );
            eventSets[EVENT_vetoableChangeListener] = new EventSetDescriptor ( org.htmlparser.beans.HTMLTextBean.class, "vetoableChangeListener", java.beans.VetoableChangeListener.class, new String[] {"vetoableChange"}, "addVetoableChangeListener", "removeVetoableChangeListener" );
            eventSets[EVENT_propertyChangeListener] = new EventSetDescriptor ( org.htmlparser.beans.HTMLTextBean.class, "propertyChangeListener", java.beans.PropertyChangeListener.class, new String[] {"propertyChange"}, "addPropertyChangeListener", "removePropertyChangeListener" );
        }
        catch( IntrospectionException e) {}//GEN-HEADEREND:Events
        
        // Here you can add code for customizing the event sets array.
        
        return eventSets;         }//GEN-LAST:Events
    
    // Method identifiers //GEN-FIRST:Methods
    private static final int METHOD_propertyChange0 = 0;
    private static final int METHOD_insert1 = 1;
    private static final int METHOD_append2 = 2;
    private static final int METHOD_replaceRange3 = 3;
    private static final int METHOD_getScrollableUnitIncrement4 = 4;
    private static final int METHOD_updateUI5 = 5;
    private static final int METHOD_addKeymap6 = 6;
    private static final int METHOD_removeKeymap7 = 7;
    private static final int METHOD_getKeymap8 = 8;
    private static final int METHOD_loadKeymap9 = 9;
    private static final int METHOD_replaceSelection10 = 10;
    private static final int METHOD_getText11 = 11;
    private static final int METHOD_modelToView12 = 12;
    private static final int METHOD_viewToModel13 = 13;
    private static final int METHOD_cut14 = 14;
    private static final int METHOD_copy15 = 15;
    private static final int METHOD_paste16 = 16;
    private static final int METHOD_moveCaretPosition17 = 17;
    private static final int METHOD_read18 = 18;
    private static final int METHOD_write19 = 19;
    private static final int METHOD_removeNotify20 = 20;
    private static final int METHOD_select21 = 21;
    private static final int METHOD_selectAll22 = 22;
    private static final int METHOD_getToolTipText23 = 23;
    private static final int METHOD_getScrollableBlockIncrement24 = 24;
    private static final int METHOD_update25 = 25;
    private static final int METHOD_paint26 = 26;
    private static final int METHOD_printAll27 = 27;
    private static final int METHOD_print28 = 28;
    private static final int METHOD_requestFocus29 = 29;
    private static final int METHOD_requestFocus30 = 30;
    private static final int METHOD_requestFocusInWindow31 = 31;
    private static final int METHOD_grabFocus32 = 32;
    private static final int METHOD_contains33 = 33;
    private static final int METHOD_getInsets34 = 34;
    private static final int METHOD_registerKeyboardAction35 = 35;
    private static final int METHOD_registerKeyboardAction36 = 36;
    private static final int METHOD_unregisterKeyboardAction37 = 37;
    private static final int METHOD_getConditionForKeyStroke38 = 38;
    private static final int METHOD_getActionForKeyStroke39 = 39;
    private static final int METHOD_resetKeyboardActions40 = 40;
    private static final int METHOD_setInputMap41 = 41;
    private static final int METHOD_getInputMap42 = 42;
    private static final int METHOD_getInputMap43 = 43;
    private static final int METHOD_requestDefaultFocus44 = 44;
    private static final int METHOD_getDefaultLocale45 = 45;
    private static final int METHOD_setDefaultLocale46 = 46;
    private static final int METHOD_getToolTipLocation47 = 47;
    private static final int METHOD_createToolTip48 = 48;
    private static final int METHOD_scrollRectToVisible49 = 49;
    private static final int METHOD_enable50 = 50;
    private static final int METHOD_disable51 = 51;
    private static final int METHOD_getClientProperty52 = 52;
    private static final int METHOD_putClientProperty53 = 53;
    private static final int METHOD_isLightweightComponent54 = 54;
    private static final int METHOD_reshape55 = 55;
    private static final int METHOD_getBounds56 = 56;
    private static final int METHOD_getSize57 = 57;
    private static final int METHOD_getLocation58 = 58;
    private static final int METHOD_computeVisibleRect59 = 59;
    private static final int METHOD_firePropertyChange60 = 60;
    private static final int METHOD_firePropertyChange61 = 61;
    private static final int METHOD_firePropertyChange62 = 62;
    private static final int METHOD_firePropertyChange63 = 63;
    private static final int METHOD_firePropertyChange64 = 64;
    private static final int METHOD_firePropertyChange65 = 65;
    private static final int METHOD_firePropertyChange66 = 66;
    private static final int METHOD_firePropertyChange67 = 67;
    private static final int METHOD_addPropertyChangeListener68 = 68;
    private static final int METHOD_removePropertyChangeListener69 = 69;
    private static final int METHOD_getPropertyChangeListeners70 = 70;
    private static final int METHOD_getListeners71 = 71;
    private static final int METHOD_addNotify72 = 72;
    private static final int METHOD_repaint73 = 73;
    private static final int METHOD_repaint74 = 74;
    private static final int METHOD_revalidate75 = 75;
    private static final int METHOD_paintImmediately76 = 76;
    private static final int METHOD_paintImmediately77 = 77;
    private static final int METHOD_countComponents78 = 78;
    private static final int METHOD_insets79 = 79;
    private static final int METHOD_add80 = 80;
    private static final int METHOD_add81 = 81;
    private static final int METHOD_add82 = 82;
    private static final int METHOD_add83 = 83;
    private static final int METHOD_add84 = 84;
    private static final int METHOD_remove85 = 85;
    private static final int METHOD_remove86 = 86;
    private static final int METHOD_removeAll87 = 87;
    private static final int METHOD_doLayout88 = 88;
    private static final int METHOD_layout89 = 89;
    private static final int METHOD_invalidate90 = 90;
    private static final int METHOD_validate91 = 91;
    private static final int METHOD_preferredSize92 = 92;
    private static final int METHOD_minimumSize93 = 93;
    private static final int METHOD_paintComponents94 = 94;
    private static final int METHOD_printComponents95 = 95;
    private static final int METHOD_deliverEvent96 = 96;
    private static final int METHOD_getComponentAt97 = 97;
    private static final int METHOD_locate98 = 98;
    private static final int METHOD_getComponentAt99 = 99;
    private static final int METHOD_findComponentAt100 = 100;
    private static final int METHOD_findComponentAt101 = 101;
    private static final int METHOD_isAncestorOf102 = 102;
    private static final int METHOD_list103 = 103;
    private static final int METHOD_list104 = 104;
    private static final int METHOD_areFocusTraversalKeysSet105 = 105;
    private static final int METHOD_isFocusCycleRoot106 = 106;
    private static final int METHOD_transferFocusBackward107 = 107;
    private static final int METHOD_transferFocusDownCycle108 = 108;
    private static final int METHOD_applyComponentOrientation109 = 109;
    private static final int METHOD_enable110 = 110;
    private static final int METHOD_enableInputMethods111 = 111;
    private static final int METHOD_show112 = 112;
    private static final int METHOD_show113 = 113;
    private static final int METHOD_hide114 = 114;
    private static final int METHOD_getLocation115 = 115;
    private static final int METHOD_location116 = 116;
    private static final int METHOD_setLocation117 = 117;
    private static final int METHOD_move118 = 118;
    private static final int METHOD_setLocation119 = 119;
    private static final int METHOD_getSize120 = 120;
    private static final int METHOD_size121 = 121;
    private static final int METHOD_setSize122 = 122;
    private static final int METHOD_resize123 = 123;
    private static final int METHOD_setSize124 = 124;
    private static final int METHOD_resize125 = 125;
    private static final int METHOD_bounds126 = 126;
    private static final int METHOD_setBounds127 = 127;
    private static final int METHOD_getFontMetrics128 = 128;
    private static final int METHOD_paintAll129 = 129;
    private static final int METHOD_repaint130 = 130;
    private static final int METHOD_repaint131 = 131;
    private static final int METHOD_repaint132 = 132;
    private static final int METHOD_imageUpdate133 = 133;
    private static final int METHOD_createImage134 = 134;
    private static final int METHOD_createImage135 = 135;
    private static final int METHOD_createVolatileImage136 = 136;
    private static final int METHOD_createVolatileImage137 = 137;
    private static final int METHOD_prepareImage138 = 138;
    private static final int METHOD_prepareImage139 = 139;
    private static final int METHOD_checkImage140 = 140;
    private static final int METHOD_checkImage141 = 141;
    private static final int METHOD_inside142 = 142;
    private static final int METHOD_contains143 = 143;
    private static final int METHOD_dispatchEvent144 = 144;
    private static final int METHOD_postEvent145 = 145;
    private static final int METHOD_handleEvent146 = 146;
    private static final int METHOD_mouseDown147 = 147;
    private static final int METHOD_mouseDrag148 = 148;
    private static final int METHOD_mouseUp149 = 149;
    private static final int METHOD_mouseMove150 = 150;
    private static final int METHOD_mouseEnter151 = 151;
    private static final int METHOD_mouseExit152 = 152;
    private static final int METHOD_keyDown153 = 153;
    private static final int METHOD_keyUp154 = 154;
    private static final int METHOD_action155 = 155;
    private static final int METHOD_gotFocus156 = 156;
    private static final int METHOD_lostFocus157 = 157;
    private static final int METHOD_transferFocus158 = 158;
    private static final int METHOD_nextFocus159 = 159;
    private static final int METHOD_transferFocusUpCycle160 = 160;
    private static final int METHOD_hasFocus161 = 161;
    private static final int METHOD_add162 = 162;
    private static final int METHOD_remove163 = 163;
    private static final int METHOD_toString164 = 164;
    private static final int METHOD_list165 = 165;
    private static final int METHOD_list166 = 166;
    private static final int METHOD_list167 = 167;

    // Method array 
    /*lazy MethodDescriptor*/
    private static MethodDescriptor[] getMdescriptor(){
        MethodDescriptor[] methods = new MethodDescriptor[168];
    
        try {
            methods[METHOD_propertyChange0] = new MethodDescriptor ( org.htmlparser.beans.HTMLTextBean.class.getMethod("propertyChange", new Class[] {java.beans.PropertyChangeEvent.class}));
            methods[METHOD_propertyChange0].setDisplayName ( "" );
            methods[METHOD_insert1] = new MethodDescriptor ( org.htmlparser.beans.HTMLTextBean.class.getMethod("insert", new Class[] {java.lang.String.class, Integer.TYPE}));
            methods[METHOD_insert1].setDisplayName ( "" );
            methods[METHOD_append2] = new MethodDescriptor ( org.htmlparser.beans.HTMLTextBean.class.getMethod("append", new Class[] {java.lang.String.class}));
            methods[METHOD_append2].setDisplayName ( "" );
            methods[METHOD_replaceRange3] = new MethodDescriptor ( org.htmlparser.beans.HTMLTextBean.class.getMethod("replaceRange", new Class[] {java.lang.String.class, Integer.TYPE, Integer.TYPE}));
            methods[METHOD_replaceRange3].setDisplayName ( "" );
            methods[METHOD_getScrollableUnitIncrement4] = new MethodDescriptor ( org.htmlparser.beans.HTMLTextBean.class.getMethod("getScrollableUnitIncrement", new Class[] {java.awt.Rectangle.class, Integer.TYPE, Integer.TYPE}));
            methods[METHOD_getScrollableUnitIncrement4].setDisplayName ( "" );
            methods[METHOD_updateUI5] = new MethodDescriptor ( org.htmlparser.beans.HTMLTextBean.class.getMethod("updateUI", new Class[] {}));
            methods[METHOD_updateUI5].setDisplayName ( "" );
            methods[METHOD_addKeymap6] = new MethodDescriptor ( org.htmlparser.beans.HTMLTextBean.class.getMethod("addKeymap", new Class[] {java.lang.String.class, javax.swing.text.Keymap.class}));
            methods[METHOD_addKeymap6].setDisplayName ( "" );
            methods[METHOD_removeKeymap7] = new MethodDescriptor ( org.htmlparser.beans.HTMLTextBean.class.getMethod("removeKeymap", new Class[] {java.lang.String.class}));
            methods[METHOD_removeKeymap7].setDisplayName ( "" );
            methods[METHOD_getKeymap8] = new MethodDescriptor ( org.htmlparser.beans.HTMLTextBean.class.getMethod("getKeymap", new Class[] {java.lang.String.class}));
            methods[METHOD_getKeymap8].setDisplayName ( "" );
            methods[METHOD_loadKeymap9] = new MethodDescriptor ( org.htmlparser.beans.HTMLTextBean.class.getMethod("loadKeymap", new Class[] {javax.swing.text.Keymap.class, Class.forName("[Ljavax.swing.text.JTextComponent$KeyBinding;"), Class.forName("[Ljavax.swing.Action;")}));
            methods[METHOD_loadKeymap9].setDisplayName ( "" );
            methods[METHOD_replaceSelection10] = new MethodDescriptor ( org.htmlparser.beans.HTMLTextBean.class.getMethod("replaceSelection", new Class[] {java.lang.String.class}));
            methods[METHOD_replaceSelection10].setDisplayName ( "" );
            methods[METHOD_getText11] = new MethodDescriptor ( org.htmlparser.beans.HTMLTextBean.class.getMethod("getText", new Class[] {Integer.TYPE, Integer.TYPE}));
            methods[METHOD_getText11].setDisplayName ( "" );
            methods[METHOD_modelToView12] = new MethodDescriptor ( org.htmlparser.beans.HTMLTextBean.class.getMethod("modelToView", new Class[] {Integer.TYPE}));
            methods[METHOD_modelToView12].setDisplayName ( "" );
            methods[METHOD_viewToModel13] = new MethodDescriptor ( org.htmlparser.beans.HTMLTextBean.class.getMethod("viewToModel", new Class[] {java.awt.Point.class}));
            methods[METHOD_viewToModel13].setDisplayName ( "" );
            methods[METHOD_cut14] = new MethodDescriptor ( org.htmlparser.beans.HTMLTextBean.class.getMethod("cut", new Class[] {}));
            methods[METHOD_cut14].setDisplayName ( "" );
            methods[METHOD_copy15] = new MethodDescriptor ( org.htmlparser.beans.HTMLTextBean.class.getMethod("copy", new Class[] {}));
            methods[METHOD_copy15].setDisplayName ( "" );
            methods[METHOD_paste16] = new MethodDescriptor ( org.htmlparser.beans.HTMLTextBean.class.getMethod("paste", new Class[] {}));
            methods[METHOD_paste16].setDisplayName ( "" );
            methods[METHOD_moveCaretPosition17] = new MethodDescriptor ( org.htmlparser.beans.HTMLTextBean.class.getMethod("moveCaretPosition", new Class[] {Integer.TYPE}));
            methods[METHOD_moveCaretPosition17].setDisplayName ( "" );
            methods[METHOD_read18] = new MethodDescriptor ( org.htmlparser.beans.HTMLTextBean.class.getMethod("read", new Class[] {java.io.Reader.class, java.lang.Object.class}));
            methods[METHOD_read18].setDisplayName ( "" );
            methods[METHOD_write19] = new MethodDescriptor ( org.htmlparser.beans.HTMLTextBean.class.getMethod("write", new Class[] {java.io.Writer.class}));
            methods[METHOD_write19].setDisplayName ( "" );
            methods[METHOD_removeNotify20] = new MethodDescriptor ( org.htmlparser.beans.HTMLTextBean.class.getMethod("removeNotify", new Class[] {}));
            methods[METHOD_removeNotify20].setDisplayName ( "" );
            methods[METHOD_select21] = new MethodDescriptor ( org.htmlparser.beans.HTMLTextBean.class.getMethod("select", new Class[] {Integer.TYPE, Integer.TYPE}));
            methods[METHOD_select21].setDisplayName ( "" );
            methods[METHOD_selectAll22] = new MethodDescriptor ( org.htmlparser.beans.HTMLTextBean.class.getMethod("selectAll", new Class[] {}));
            methods[METHOD_selectAll22].setDisplayName ( "" );
            methods[METHOD_getToolTipText23] = new MethodDescriptor ( org.htmlparser.beans.HTMLTextBean.class.getMethod("getToolTipText", new Class[] {java.awt.event.MouseEvent.class}));
            methods[METHOD_getToolTipText23].setDisplayName ( "" );
            methods[METHOD_getScrollableBlockIncrement24] = new MethodDescriptor ( org.htmlparser.beans.HTMLTextBean.class.getMethod("getScrollableBlockIncrement", new Class[] {java.awt.Rectangle.class, Integer.TYPE, Integer.TYPE}));
            methods[METHOD_getScrollableBlockIncrement24].setDisplayName ( "" );
            methods[METHOD_update25] = new MethodDescriptor ( org.htmlparser.beans.HTMLTextBean.class.getMethod("update", new Class[] {java.awt.Graphics.class}));
            methods[METHOD_update25].setDisplayName ( "" );
            methods[METHOD_paint26] = new MethodDescriptor ( org.htmlparser.beans.HTMLTextBean.class.getMethod("paint", new Class[] {java.awt.Graphics.class}));
            methods[METHOD_paint26].setDisplayName ( "" );
            methods[METHOD_printAll27] = new MethodDescriptor ( org.htmlparser.beans.HTMLTextBean.class.getMethod("printAll", new Class[] {java.awt.Graphics.class}));
            methods[METHOD_printAll27].setDisplayName ( "" );
            methods[METHOD_print28] = new MethodDescriptor ( org.htmlparser.beans.HTMLTextBean.class.getMethod("print", new Class[] {java.awt.Graphics.class}));
            methods[METHOD_print28].setDisplayName ( "" );
            methods[METHOD_requestFocus29] = new MethodDescriptor ( org.htmlparser.beans.HTMLTextBean.class.getMethod("requestFocus", new Class[] {}));
            methods[METHOD_requestFocus29].setDisplayName ( "" );
            methods[METHOD_requestFocus30] = new MethodDescriptor ( org.htmlparser.beans.HTMLTextBean.class.getMethod("requestFocus", new Class[] {Boolean.TYPE}));
            methods[METHOD_requestFocus30].setDisplayName ( "" );
            methods[METHOD_requestFocusInWindow31] = new MethodDescriptor ( org.htmlparser.beans.HTMLTextBean.class.getMethod("requestFocusInWindow", new Class[] {}));
            methods[METHOD_requestFocusInWindow31].setDisplayName ( "" );
            methods[METHOD_grabFocus32] = new MethodDescriptor ( org.htmlparser.beans.HTMLTextBean.class.getMethod("grabFocus", new Class[] {}));
            methods[METHOD_grabFocus32].setDisplayName ( "" );
            methods[METHOD_contains33] = new MethodDescriptor ( org.htmlparser.beans.HTMLTextBean.class.getMethod("contains", new Class[] {Integer.TYPE, Integer.TYPE}));
            methods[METHOD_contains33].setDisplayName ( "" );
            methods[METHOD_getInsets34] = new MethodDescriptor ( org.htmlparser.beans.HTMLTextBean.class.getMethod("getInsets", new Class[] {java.awt.Insets.class}));
            methods[METHOD_getInsets34].setDisplayName ( "" );
            methods[METHOD_registerKeyboardAction35] = new MethodDescriptor ( org.htmlparser.beans.HTMLTextBean.class.getMethod("registerKeyboardAction", new Class[] {java.awt.event.ActionListener.class, java.lang.String.class, javax.swing.KeyStroke.class, Integer.TYPE}));
            methods[METHOD_registerKeyboardAction35].setDisplayName ( "" );
            methods[METHOD_registerKeyboardAction36] = new MethodDescriptor ( org.htmlparser.beans.HTMLTextBean.class.getMethod("registerKeyboardAction", new Class[] {java.awt.event.ActionListener.class, javax.swing.KeyStroke.class, Integer.TYPE}));
            methods[METHOD_registerKeyboardAction36].setDisplayName ( "" );
            methods[METHOD_unregisterKeyboardAction37] = new MethodDescriptor ( org.htmlparser.beans.HTMLTextBean.class.getMethod("unregisterKeyboardAction", new Class[] {javax.swing.KeyStroke.class}));
            methods[METHOD_unregisterKeyboardAction37].setDisplayName ( "" );
            methods[METHOD_getConditionForKeyStroke38] = new MethodDescriptor ( org.htmlparser.beans.HTMLTextBean.class.getMethod("getConditionForKeyStroke", new Class[] {javax.swing.KeyStroke.class}));
            methods[METHOD_getConditionForKeyStroke38].setDisplayName ( "" );
            methods[METHOD_getActionForKeyStroke39] = new MethodDescriptor ( org.htmlparser.beans.HTMLTextBean.class.getMethod("getActionForKeyStroke", new Class[] {javax.swing.KeyStroke.class}));
            methods[METHOD_getActionForKeyStroke39].setDisplayName ( "" );
            methods[METHOD_resetKeyboardActions40] = new MethodDescriptor ( org.htmlparser.beans.HTMLTextBean.class.getMethod("resetKeyboardActions", new Class[] {}));
            methods[METHOD_resetKeyboardActions40].setDisplayName ( "" );
            methods[METHOD_setInputMap41] = new MethodDescriptor ( org.htmlparser.beans.HTMLTextBean.class.getMethod("setInputMap", new Class[] {Integer.TYPE, javax.swing.InputMap.class}));
            methods[METHOD_setInputMap41].setDisplayName ( "" );
            methods[METHOD_getInputMap42] = new MethodDescriptor ( org.htmlparser.beans.HTMLTextBean.class.getMethod("getInputMap", new Class[] {Integer.TYPE}));
            methods[METHOD_getInputMap42].setDisplayName ( "" );
            methods[METHOD_getInputMap43] = new MethodDescriptor ( org.htmlparser.beans.HTMLTextBean.class.getMethod("getInputMap", new Class[] {}));
            methods[METHOD_getInputMap43].setDisplayName ( "" );
            methods[METHOD_requestDefaultFocus44] = new MethodDescriptor ( org.htmlparser.beans.HTMLTextBean.class.getMethod("requestDefaultFocus", new Class[] {}));
            methods[METHOD_requestDefaultFocus44].setDisplayName ( "" );
            methods[METHOD_getDefaultLocale45] = new MethodDescriptor ( org.htmlparser.beans.HTMLTextBean.class.getMethod("getDefaultLocale", new Class[] {}));
            methods[METHOD_getDefaultLocale45].setDisplayName ( "" );
            methods[METHOD_setDefaultLocale46] = new MethodDescriptor ( org.htmlparser.beans.HTMLTextBean.class.getMethod("setDefaultLocale", new Class[] {java.util.Locale.class}));
            methods[METHOD_setDefaultLocale46].setDisplayName ( "" );
            methods[METHOD_getToolTipLocation47] = new MethodDescriptor ( org.htmlparser.beans.HTMLTextBean.class.getMethod("getToolTipLocation", new Class[] {java.awt.event.MouseEvent.class}));
            methods[METHOD_getToolTipLocation47].setDisplayName ( "" );
            methods[METHOD_createToolTip48] = new MethodDescriptor ( org.htmlparser.beans.HTMLTextBean.class.getMethod("createToolTip", new Class[] {}));
            methods[METHOD_createToolTip48].setDisplayName ( "" );
            methods[METHOD_scrollRectToVisible49] = new MethodDescriptor ( org.htmlparser.beans.HTMLTextBean.class.getMethod("scrollRectToVisible", new Class[] {java.awt.Rectangle.class}));
            methods[METHOD_scrollRectToVisible49].setDisplayName ( "" );
            methods[METHOD_enable50] = new MethodDescriptor ( org.htmlparser.beans.HTMLTextBean.class.getMethod("enable", new Class[] {}));
            methods[METHOD_enable50].setDisplayName ( "" );
            methods[METHOD_disable51] = new MethodDescriptor ( org.htmlparser.beans.HTMLTextBean.class.getMethod("disable", new Class[] {}));
            methods[METHOD_disable51].setDisplayName ( "" );
            methods[METHOD_getClientProperty52] = new MethodDescriptor ( org.htmlparser.beans.HTMLTextBean.class.getMethod("getClientProperty", new Class[] {java.lang.Object.class}));
            methods[METHOD_getClientProperty52].setDisplayName ( "" );
            methods[METHOD_putClientProperty53] = new MethodDescriptor ( org.htmlparser.beans.HTMLTextBean.class.getMethod("putClientProperty", new Class[] {java.lang.Object.class, java.lang.Object.class}));
            methods[METHOD_putClientProperty53].setDisplayName ( "" );
            methods[METHOD_isLightweightComponent54] = new MethodDescriptor ( org.htmlparser.beans.HTMLTextBean.class.getMethod("isLightweightComponent", new Class[] {java.awt.Component.class}));
            methods[METHOD_isLightweightComponent54].setDisplayName ( "" );
            methods[METHOD_reshape55] = new MethodDescriptor ( org.htmlparser.beans.HTMLTextBean.class.getMethod("reshape", new Class[] {Integer.TYPE, Integer.TYPE, Integer.TYPE, Integer.TYPE}));
            methods[METHOD_reshape55].setDisplayName ( "" );
            methods[METHOD_getBounds56] = new MethodDescriptor ( org.htmlparser.beans.HTMLTextBean.class.getMethod("getBounds", new Class[] {java.awt.Rectangle.class}));
            methods[METHOD_getBounds56].setDisplayName ( "" );
            methods[METHOD_getSize57] = new MethodDescriptor ( org.htmlparser.beans.HTMLTextBean.class.getMethod("getSize", new Class[] {java.awt.Dimension.class}));
            methods[METHOD_getSize57].setDisplayName ( "" );
            methods[METHOD_getLocation58] = new MethodDescriptor ( org.htmlparser.beans.HTMLTextBean.class.getMethod("getLocation", new Class[] {java.awt.Point.class}));
            methods[METHOD_getLocation58].setDisplayName ( "" );
            methods[METHOD_computeVisibleRect59] = new MethodDescriptor ( org.htmlparser.beans.HTMLTextBean.class.getMethod("computeVisibleRect", new Class[] {java.awt.Rectangle.class}));
            methods[METHOD_computeVisibleRect59].setDisplayName ( "" );
            methods[METHOD_firePropertyChange60] = new MethodDescriptor ( org.htmlparser.beans.HTMLTextBean.class.getMethod("firePropertyChange", new Class[] {java.lang.String.class, Byte.TYPE, Byte.TYPE}));
            methods[METHOD_firePropertyChange60].setDisplayName ( "" );
            methods[METHOD_firePropertyChange61] = new MethodDescriptor ( org.htmlparser.beans.HTMLTextBean.class.getMethod("firePropertyChange", new Class[] {java.lang.String.class, Character.TYPE, Character.TYPE}));
            methods[METHOD_firePropertyChange61].setDisplayName ( "" );
            methods[METHOD_firePropertyChange62] = new MethodDescriptor ( org.htmlparser.beans.HTMLTextBean.class.getMethod("firePropertyChange", new Class[] {java.lang.String.class, Short.TYPE, Short.TYPE}));
            methods[METHOD_firePropertyChange62].setDisplayName ( "" );
            methods[METHOD_firePropertyChange63] = new MethodDescriptor ( org.htmlparser.beans.HTMLTextBean.class.getMethod("firePropertyChange", new Class[] {java.lang.String.class, Integer.TYPE, Integer.TYPE}));
            methods[METHOD_firePropertyChange63].setDisplayName ( "" );
            methods[METHOD_firePropertyChange64] = new MethodDescriptor ( org.htmlparser.beans.HTMLTextBean.class.getMethod("firePropertyChange", new Class[] {java.lang.String.class, Long.TYPE, Long.TYPE}));
            methods[METHOD_firePropertyChange64].setDisplayName ( "" );
            methods[METHOD_firePropertyChange65] = new MethodDescriptor ( org.htmlparser.beans.HTMLTextBean.class.getMethod("firePropertyChange", new Class[] {java.lang.String.class, Float.TYPE, Float.TYPE}));
            methods[METHOD_firePropertyChange65].setDisplayName ( "" );
            methods[METHOD_firePropertyChange66] = new MethodDescriptor ( org.htmlparser.beans.HTMLTextBean.class.getMethod("firePropertyChange", new Class[] {java.lang.String.class, Double.TYPE, Double.TYPE}));
            methods[METHOD_firePropertyChange66].setDisplayName ( "" );
            methods[METHOD_firePropertyChange67] = new MethodDescriptor ( org.htmlparser.beans.HTMLTextBean.class.getMethod("firePropertyChange", new Class[] {java.lang.String.class, Boolean.TYPE, Boolean.TYPE}));
            methods[METHOD_firePropertyChange67].setDisplayName ( "" );
            methods[METHOD_addPropertyChangeListener68] = new MethodDescriptor ( org.htmlparser.beans.HTMLTextBean.class.getMethod("addPropertyChangeListener", new Class[] {java.lang.String.class, java.beans.PropertyChangeListener.class}));
            methods[METHOD_addPropertyChangeListener68].setDisplayName ( "" );
            methods[METHOD_removePropertyChangeListener69] = new MethodDescriptor ( org.htmlparser.beans.HTMLTextBean.class.getMethod("removePropertyChangeListener", new Class[] {java.lang.String.class, java.beans.PropertyChangeListener.class}));
            methods[METHOD_removePropertyChangeListener69].setDisplayName ( "" );
            methods[METHOD_getPropertyChangeListeners70] = new MethodDescriptor ( org.htmlparser.beans.HTMLTextBean.class.getMethod("getPropertyChangeListeners", new Class[] {java.lang.String.class}));
            methods[METHOD_getPropertyChangeListeners70].setDisplayName ( "" );
            methods[METHOD_getListeners71] = new MethodDescriptor ( org.htmlparser.beans.HTMLTextBean.class.getMethod("getListeners", new Class[] {java.lang.Class.class}));
            methods[METHOD_getListeners71].setDisplayName ( "" );
            methods[METHOD_addNotify72] = new MethodDescriptor ( org.htmlparser.beans.HTMLTextBean.class.getMethod("addNotify", new Class[] {}));
            methods[METHOD_addNotify72].setDisplayName ( "" );
            methods[METHOD_repaint73] = new MethodDescriptor ( org.htmlparser.beans.HTMLTextBean.class.getMethod("repaint", new Class[] {Long.TYPE, Integer.TYPE, Integer.TYPE, Integer.TYPE, Integer.TYPE}));
            methods[METHOD_repaint73].setDisplayName ( "" );
            methods[METHOD_repaint74] = new MethodDescriptor ( org.htmlparser.beans.HTMLTextBean.class.getMethod("repaint", new Class[] {java.awt.Rectangle.class}));
            methods[METHOD_repaint74].setDisplayName ( "" );
            methods[METHOD_revalidate75] = new MethodDescriptor ( org.htmlparser.beans.HTMLTextBean.class.getMethod("revalidate", new Class[] {}));
            methods[METHOD_revalidate75].setDisplayName ( "" );
            methods[METHOD_paintImmediately76] = new MethodDescriptor ( org.htmlparser.beans.HTMLTextBean.class.getMethod("paintImmediately", new Class[] {Integer.TYPE, Integer.TYPE, Integer.TYPE, Integer.TYPE}));
            methods[METHOD_paintImmediately76].setDisplayName ( "" );
            methods[METHOD_paintImmediately77] = new MethodDescriptor ( org.htmlparser.beans.HTMLTextBean.class.getMethod("paintImmediately", new Class[] {java.awt.Rectangle.class}));
            methods[METHOD_paintImmediately77].setDisplayName ( "" );
            methods[METHOD_countComponents78] = new MethodDescriptor ( org.htmlparser.beans.HTMLTextBean.class.getMethod("countComponents", new Class[] {}));
            methods[METHOD_countComponents78].setDisplayName ( "" );
            methods[METHOD_insets79] = new MethodDescriptor ( org.htmlparser.beans.HTMLTextBean.class.getMethod("insets", new Class[] {}));
            methods[METHOD_insets79].setDisplayName ( "" );
            methods[METHOD_add80] = new MethodDescriptor ( org.htmlparser.beans.HTMLTextBean.class.getMethod("add", new Class[] {java.awt.Component.class}));
            methods[METHOD_add80].setDisplayName ( "" );
            methods[METHOD_add81] = new MethodDescriptor ( org.htmlparser.beans.HTMLTextBean.class.getMethod("add", new Class[] {java.lang.String.class, java.awt.Component.class}));
            methods[METHOD_add81].setDisplayName ( "" );
            methods[METHOD_add82] = new MethodDescriptor ( org.htmlparser.beans.HTMLTextBean.class.getMethod("add", new Class[] {java.awt.Component.class, Integer.TYPE}));
            methods[METHOD_add82].setDisplayName ( "" );
            methods[METHOD_add83] = new MethodDescriptor ( org.htmlparser.beans.HTMLTextBean.class.getMethod("add", new Class[] {java.awt.Component.class, java.lang.Object.class}));
            methods[METHOD_add83].setDisplayName ( "" );
            methods[METHOD_add84] = new MethodDescriptor ( org.htmlparser.beans.HTMLTextBean.class.getMethod("add", new Class[] {java.awt.Component.class, java.lang.Object.class, Integer.TYPE}));
            methods[METHOD_add84].setDisplayName ( "" );
            methods[METHOD_remove85] = new MethodDescriptor ( org.htmlparser.beans.HTMLTextBean.class.getMethod("remove", new Class[] {Integer.TYPE}));
            methods[METHOD_remove85].setDisplayName ( "" );
            methods[METHOD_remove86] = new MethodDescriptor ( org.htmlparser.beans.HTMLTextBean.class.getMethod("remove", new Class[] {java.awt.Component.class}));
            methods[METHOD_remove86].setDisplayName ( "" );
            methods[METHOD_removeAll87] = new MethodDescriptor ( org.htmlparser.beans.HTMLTextBean.class.getMethod("removeAll", new Class[] {}));
            methods[METHOD_removeAll87].setDisplayName ( "" );
            methods[METHOD_doLayout88] = new MethodDescriptor ( org.htmlparser.beans.HTMLTextBean.class.getMethod("doLayout", new Class[] {}));
            methods[METHOD_doLayout88].setDisplayName ( "" );
            methods[METHOD_layout89] = new MethodDescriptor ( org.htmlparser.beans.HTMLTextBean.class.getMethod("layout", new Class[] {}));
            methods[METHOD_layout89].setDisplayName ( "" );
            methods[METHOD_invalidate90] = new MethodDescriptor ( org.htmlparser.beans.HTMLTextBean.class.getMethod("invalidate", new Class[] {}));
            methods[METHOD_invalidate90].setDisplayName ( "" );
            methods[METHOD_validate91] = new MethodDescriptor ( org.htmlparser.beans.HTMLTextBean.class.getMethod("validate", new Class[] {}));
            methods[METHOD_validate91].setDisplayName ( "" );
            methods[METHOD_preferredSize92] = new MethodDescriptor ( org.htmlparser.beans.HTMLTextBean.class.getMethod("preferredSize", new Class[] {}));
            methods[METHOD_preferredSize92].setDisplayName ( "" );
            methods[METHOD_minimumSize93] = new MethodDescriptor ( org.htmlparser.beans.HTMLTextBean.class.getMethod("minimumSize", new Class[] {}));
            methods[METHOD_minimumSize93].setDisplayName ( "" );
            methods[METHOD_paintComponents94] = new MethodDescriptor ( org.htmlparser.beans.HTMLTextBean.class.getMethod("paintComponents", new Class[] {java.awt.Graphics.class}));
            methods[METHOD_paintComponents94].setDisplayName ( "" );
            methods[METHOD_printComponents95] = new MethodDescriptor ( org.htmlparser.beans.HTMLTextBean.class.getMethod("printComponents", new Class[] {java.awt.Graphics.class}));
            methods[METHOD_printComponents95].setDisplayName ( "" );
            methods[METHOD_deliverEvent96] = new MethodDescriptor ( org.htmlparser.beans.HTMLTextBean.class.getMethod("deliverEvent", new Class[] {java.awt.Event.class}));
            methods[METHOD_deliverEvent96].setDisplayName ( "" );
            methods[METHOD_getComponentAt97] = new MethodDescriptor ( org.htmlparser.beans.HTMLTextBean.class.getMethod("getComponentAt", new Class[] {Integer.TYPE, Integer.TYPE}));
            methods[METHOD_getComponentAt97].setDisplayName ( "" );
            methods[METHOD_locate98] = new MethodDescriptor ( org.htmlparser.beans.HTMLTextBean.class.getMethod("locate", new Class[] {Integer.TYPE, Integer.TYPE}));
            methods[METHOD_locate98].setDisplayName ( "" );
            methods[METHOD_getComponentAt99] = new MethodDescriptor ( org.htmlparser.beans.HTMLTextBean.class.getMethod("getComponentAt", new Class[] {java.awt.Point.class}));
            methods[METHOD_getComponentAt99].setDisplayName ( "" );
            methods[METHOD_findComponentAt100] = new MethodDescriptor ( org.htmlparser.beans.HTMLTextBean.class.getMethod("findComponentAt", new Class[] {Integer.TYPE, Integer.TYPE}));
            methods[METHOD_findComponentAt100].setDisplayName ( "" );
            methods[METHOD_findComponentAt101] = new MethodDescriptor ( org.htmlparser.beans.HTMLTextBean.class.getMethod("findComponentAt", new Class[] {java.awt.Point.class}));
            methods[METHOD_findComponentAt101].setDisplayName ( "" );
            methods[METHOD_isAncestorOf102] = new MethodDescriptor ( org.htmlparser.beans.HTMLTextBean.class.getMethod("isAncestorOf", new Class[] {java.awt.Component.class}));
            methods[METHOD_isAncestorOf102].setDisplayName ( "" );
            methods[METHOD_list103] = new MethodDescriptor ( org.htmlparser.beans.HTMLTextBean.class.getMethod("list", new Class[] {java.io.PrintStream.class, Integer.TYPE}));
            methods[METHOD_list103].setDisplayName ( "" );
            methods[METHOD_list104] = new MethodDescriptor ( org.htmlparser.beans.HTMLTextBean.class.getMethod("list", new Class[] {java.io.PrintWriter.class, Integer.TYPE}));
            methods[METHOD_list104].setDisplayName ( "" );
            methods[METHOD_areFocusTraversalKeysSet105] = new MethodDescriptor ( org.htmlparser.beans.HTMLTextBean.class.getMethod("areFocusTraversalKeysSet", new Class[] {Integer.TYPE}));
            methods[METHOD_areFocusTraversalKeysSet105].setDisplayName ( "" );
            methods[METHOD_isFocusCycleRoot106] = new MethodDescriptor ( org.htmlparser.beans.HTMLTextBean.class.getMethod("isFocusCycleRoot", new Class[] {java.awt.Container.class}));
            methods[METHOD_isFocusCycleRoot106].setDisplayName ( "" );
            methods[METHOD_transferFocusBackward107] = new MethodDescriptor ( org.htmlparser.beans.HTMLTextBean.class.getMethod("transferFocusBackward", new Class[] {}));
            methods[METHOD_transferFocusBackward107].setDisplayName ( "" );
            methods[METHOD_transferFocusDownCycle108] = new MethodDescriptor ( org.htmlparser.beans.HTMLTextBean.class.getMethod("transferFocusDownCycle", new Class[] {}));
            methods[METHOD_transferFocusDownCycle108].setDisplayName ( "" );
            methods[METHOD_applyComponentOrientation109] = new MethodDescriptor ( org.htmlparser.beans.HTMLTextBean.class.getMethod("applyComponentOrientation", new Class[] {java.awt.ComponentOrientation.class}));
            methods[METHOD_applyComponentOrientation109].setDisplayName ( "" );
            methods[METHOD_enable110] = new MethodDescriptor ( org.htmlparser.beans.HTMLTextBean.class.getMethod("enable", new Class[] {Boolean.TYPE}));
            methods[METHOD_enable110].setDisplayName ( "" );
            methods[METHOD_enableInputMethods111] = new MethodDescriptor ( org.htmlparser.beans.HTMLTextBean.class.getMethod("enableInputMethods", new Class[] {Boolean.TYPE}));
            methods[METHOD_enableInputMethods111].setDisplayName ( "" );
            methods[METHOD_show112] = new MethodDescriptor ( org.htmlparser.beans.HTMLTextBean.class.getMethod("show", new Class[] {}));
            methods[METHOD_show112].setDisplayName ( "" );
            methods[METHOD_show113] = new MethodDescriptor ( org.htmlparser.beans.HTMLTextBean.class.getMethod("show", new Class[] {Boolean.TYPE}));
            methods[METHOD_show113].setDisplayName ( "" );
            methods[METHOD_hide114] = new MethodDescriptor ( org.htmlparser.beans.HTMLTextBean.class.getMethod("hide", new Class[] {}));
            methods[METHOD_hide114].setDisplayName ( "" );
            methods[METHOD_getLocation115] = new MethodDescriptor ( org.htmlparser.beans.HTMLTextBean.class.getMethod("getLocation", new Class[] {}));
            methods[METHOD_getLocation115].setDisplayName ( "" );
            methods[METHOD_location116] = new MethodDescriptor ( org.htmlparser.beans.HTMLTextBean.class.getMethod("location", new Class[] {}));
            methods[METHOD_location116].setDisplayName ( "" );
            methods[METHOD_setLocation117] = new MethodDescriptor ( org.htmlparser.beans.HTMLTextBean.class.getMethod("setLocation", new Class[] {Integer.TYPE, Integer.TYPE}));
            methods[METHOD_setLocation117].setDisplayName ( "" );
            methods[METHOD_move118] = new MethodDescriptor ( org.htmlparser.beans.HTMLTextBean.class.getMethod("move", new Class[] {Integer.TYPE, Integer.TYPE}));
            methods[METHOD_move118].setDisplayName ( "" );
            methods[METHOD_setLocation119] = new MethodDescriptor ( org.htmlparser.beans.HTMLTextBean.class.getMethod("setLocation", new Class[] {java.awt.Point.class}));
            methods[METHOD_setLocation119].setDisplayName ( "" );
            methods[METHOD_getSize120] = new MethodDescriptor ( org.htmlparser.beans.HTMLTextBean.class.getMethod("getSize", new Class[] {}));
            methods[METHOD_getSize120].setDisplayName ( "" );
            methods[METHOD_size121] = new MethodDescriptor ( org.htmlparser.beans.HTMLTextBean.class.getMethod("size", new Class[] {}));
            methods[METHOD_size121].setDisplayName ( "" );
            methods[METHOD_setSize122] = new MethodDescriptor ( org.htmlparser.beans.HTMLTextBean.class.getMethod("setSize", new Class[] {Integer.TYPE, Integer.TYPE}));
            methods[METHOD_setSize122].setDisplayName ( "" );
            methods[METHOD_resize123] = new MethodDescriptor ( org.htmlparser.beans.HTMLTextBean.class.getMethod("resize", new Class[] {Integer.TYPE, Integer.TYPE}));
            methods[METHOD_resize123].setDisplayName ( "" );
            methods[METHOD_setSize124] = new MethodDescriptor ( org.htmlparser.beans.HTMLTextBean.class.getMethod("setSize", new Class[] {java.awt.Dimension.class}));
            methods[METHOD_setSize124].setDisplayName ( "" );
            methods[METHOD_resize125] = new MethodDescriptor ( org.htmlparser.beans.HTMLTextBean.class.getMethod("resize", new Class[] {java.awt.Dimension.class}));
            methods[METHOD_resize125].setDisplayName ( "" );
            methods[METHOD_bounds126] = new MethodDescriptor ( org.htmlparser.beans.HTMLTextBean.class.getMethod("bounds", new Class[] {}));
            methods[METHOD_bounds126].setDisplayName ( "" );
            methods[METHOD_setBounds127] = new MethodDescriptor ( org.htmlparser.beans.HTMLTextBean.class.getMethod("setBounds", new Class[] {Integer.TYPE, Integer.TYPE, Integer.TYPE, Integer.TYPE}));
            methods[METHOD_setBounds127].setDisplayName ( "" );
            methods[METHOD_getFontMetrics128] = new MethodDescriptor ( org.htmlparser.beans.HTMLTextBean.class.getMethod("getFontMetrics", new Class[] {java.awt.Font.class}));
            methods[METHOD_getFontMetrics128].setDisplayName ( "" );
            methods[METHOD_paintAll129] = new MethodDescriptor ( org.htmlparser.beans.HTMLTextBean.class.getMethod("paintAll", new Class[] {java.awt.Graphics.class}));
            methods[METHOD_paintAll129].setDisplayName ( "" );
            methods[METHOD_repaint130] = new MethodDescriptor ( org.htmlparser.beans.HTMLTextBean.class.getMethod("repaint", new Class[] {}));
            methods[METHOD_repaint130].setDisplayName ( "" );
            methods[METHOD_repaint131] = new MethodDescriptor ( org.htmlparser.beans.HTMLTextBean.class.getMethod("repaint", new Class[] {Long.TYPE}));
            methods[METHOD_repaint131].setDisplayName ( "" );
            methods[METHOD_repaint132] = new MethodDescriptor ( org.htmlparser.beans.HTMLTextBean.class.getMethod("repaint", new Class[] {Integer.TYPE, Integer.TYPE, Integer.TYPE, Integer.TYPE}));
            methods[METHOD_repaint132].setDisplayName ( "" );
            methods[METHOD_imageUpdate133] = new MethodDescriptor ( org.htmlparser.beans.HTMLTextBean.class.getMethod("imageUpdate", new Class[] {java.awt.Image.class, Integer.TYPE, Integer.TYPE, Integer.TYPE, Integer.TYPE, Integer.TYPE}));
            methods[METHOD_imageUpdate133].setDisplayName ( "" );
            methods[METHOD_createImage134] = new MethodDescriptor ( org.htmlparser.beans.HTMLTextBean.class.getMethod("createImage", new Class[] {java.awt.image.ImageProducer.class}));
            methods[METHOD_createImage134].setDisplayName ( "" );
            methods[METHOD_createImage135] = new MethodDescriptor ( org.htmlparser.beans.HTMLTextBean.class.getMethod("createImage", new Class[] {Integer.TYPE, Integer.TYPE}));
            methods[METHOD_createImage135].setDisplayName ( "" );
            methods[METHOD_createVolatileImage136] = new MethodDescriptor ( org.htmlparser.beans.HTMLTextBean.class.getMethod("createVolatileImage", new Class[] {Integer.TYPE, Integer.TYPE}));
            methods[METHOD_createVolatileImage136].setDisplayName ( "" );
            methods[METHOD_createVolatileImage137] = new MethodDescriptor ( org.htmlparser.beans.HTMLTextBean.class.getMethod("createVolatileImage", new Class[] {Integer.TYPE, Integer.TYPE, java.awt.ImageCapabilities.class}));
            methods[METHOD_createVolatileImage137].setDisplayName ( "" );
            methods[METHOD_prepareImage138] = new MethodDescriptor ( org.htmlparser.beans.HTMLTextBean.class.getMethod("prepareImage", new Class[] {java.awt.Image.class, java.awt.image.ImageObserver.class}));
            methods[METHOD_prepareImage138].setDisplayName ( "" );
            methods[METHOD_prepareImage139] = new MethodDescriptor ( org.htmlparser.beans.HTMLTextBean.class.getMethod("prepareImage", new Class[] {java.awt.Image.class, Integer.TYPE, Integer.TYPE, java.awt.image.ImageObserver.class}));
            methods[METHOD_prepareImage139].setDisplayName ( "" );
            methods[METHOD_checkImage140] = new MethodDescriptor ( org.htmlparser.beans.HTMLTextBean.class.getMethod("checkImage", new Class[] {java.awt.Image.class, java.awt.image.ImageObserver.class}));
            methods[METHOD_checkImage140].setDisplayName ( "" );
            methods[METHOD_checkImage141] = new MethodDescriptor ( org.htmlparser.beans.HTMLTextBean.class.getMethod("checkImage", new Class[] {java.awt.Image.class, Integer.TYPE, Integer.TYPE, java.awt.image.ImageObserver.class}));
            methods[METHOD_checkImage141].setDisplayName ( "" );
            methods[METHOD_inside142] = new MethodDescriptor ( org.htmlparser.beans.HTMLTextBean.class.getMethod("inside", new Class[] {Integer.TYPE, Integer.TYPE}));
            methods[METHOD_inside142].setDisplayName ( "" );
            methods[METHOD_contains143] = new MethodDescriptor ( org.htmlparser.beans.HTMLTextBean.class.getMethod("contains", new Class[] {java.awt.Point.class}));
            methods[METHOD_contains143].setDisplayName ( "" );
            methods[METHOD_dispatchEvent144] = new MethodDescriptor ( org.htmlparser.beans.HTMLTextBean.class.getMethod("dispatchEvent", new Class[] {java.awt.AWTEvent.class}));
            methods[METHOD_dispatchEvent144].setDisplayName ( "" );
            methods[METHOD_postEvent145] = new MethodDescriptor ( org.htmlparser.beans.HTMLTextBean.class.getMethod("postEvent", new Class[] {java.awt.Event.class}));
            methods[METHOD_postEvent145].setDisplayName ( "" );
            methods[METHOD_handleEvent146] = new MethodDescriptor ( org.htmlparser.beans.HTMLTextBean.class.getMethod("handleEvent", new Class[] {java.awt.Event.class}));
            methods[METHOD_handleEvent146].setDisplayName ( "" );
            methods[METHOD_mouseDown147] = new MethodDescriptor ( org.htmlparser.beans.HTMLTextBean.class.getMethod("mouseDown", new Class[] {java.awt.Event.class, Integer.TYPE, Integer.TYPE}));
            methods[METHOD_mouseDown147].setDisplayName ( "" );
            methods[METHOD_mouseDrag148] = new MethodDescriptor ( org.htmlparser.beans.HTMLTextBean.class.getMethod("mouseDrag", new Class[] {java.awt.Event.class, Integer.TYPE, Integer.TYPE}));
            methods[METHOD_mouseDrag148].setDisplayName ( "" );
            methods[METHOD_mouseUp149] = new MethodDescriptor ( org.htmlparser.beans.HTMLTextBean.class.getMethod("mouseUp", new Class[] {java.awt.Event.class, Integer.TYPE, Integer.TYPE}));
            methods[METHOD_mouseUp149].setDisplayName ( "" );
            methods[METHOD_mouseMove150] = new MethodDescriptor ( org.htmlparser.beans.HTMLTextBean.class.getMethod("mouseMove", new Class[] {java.awt.Event.class, Integer.TYPE, Integer.TYPE}));
            methods[METHOD_mouseMove150].setDisplayName ( "" );
            methods[METHOD_mouseEnter151] = new MethodDescriptor ( org.htmlparser.beans.HTMLTextBean.class.getMethod("mouseEnter", new Class[] {java.awt.Event.class, Integer.TYPE, Integer.TYPE}));
            methods[METHOD_mouseEnter151].setDisplayName ( "" );
            methods[METHOD_mouseExit152] = new MethodDescriptor ( org.htmlparser.beans.HTMLTextBean.class.getMethod("mouseExit", new Class[] {java.awt.Event.class, Integer.TYPE, Integer.TYPE}));
            methods[METHOD_mouseExit152].setDisplayName ( "" );
            methods[METHOD_keyDown153] = new MethodDescriptor ( org.htmlparser.beans.HTMLTextBean.class.getMethod("keyDown", new Class[] {java.awt.Event.class, Integer.TYPE}));
            methods[METHOD_keyDown153].setDisplayName ( "" );
            methods[METHOD_keyUp154] = new MethodDescriptor ( org.htmlparser.beans.HTMLTextBean.class.getMethod("keyUp", new Class[] {java.awt.Event.class, Integer.TYPE}));
            methods[METHOD_keyUp154].setDisplayName ( "" );
            methods[METHOD_action155] = new MethodDescriptor ( org.htmlparser.beans.HTMLTextBean.class.getMethod("action", new Class[] {java.awt.Event.class, java.lang.Object.class}));
            methods[METHOD_action155].setDisplayName ( "" );
            methods[METHOD_gotFocus156] = new MethodDescriptor ( org.htmlparser.beans.HTMLTextBean.class.getMethod("gotFocus", new Class[] {java.awt.Event.class, java.lang.Object.class}));
            methods[METHOD_gotFocus156].setDisplayName ( "" );
            methods[METHOD_lostFocus157] = new MethodDescriptor ( org.htmlparser.beans.HTMLTextBean.class.getMethod("lostFocus", new Class[] {java.awt.Event.class, java.lang.Object.class}));
            methods[METHOD_lostFocus157].setDisplayName ( "" );
            methods[METHOD_transferFocus158] = new MethodDescriptor ( org.htmlparser.beans.HTMLTextBean.class.getMethod("transferFocus", new Class[] {}));
            methods[METHOD_transferFocus158].setDisplayName ( "" );
            methods[METHOD_nextFocus159] = new MethodDescriptor ( org.htmlparser.beans.HTMLTextBean.class.getMethod("nextFocus", new Class[] {}));
            methods[METHOD_nextFocus159].setDisplayName ( "" );
            methods[METHOD_transferFocusUpCycle160] = new MethodDescriptor ( org.htmlparser.beans.HTMLTextBean.class.getMethod("transferFocusUpCycle", new Class[] {}));
            methods[METHOD_transferFocusUpCycle160].setDisplayName ( "" );
            methods[METHOD_hasFocus161] = new MethodDescriptor ( org.htmlparser.beans.HTMLTextBean.class.getMethod("hasFocus", new Class[] {}));
            methods[METHOD_hasFocus161].setDisplayName ( "" );
            methods[METHOD_add162] = new MethodDescriptor ( org.htmlparser.beans.HTMLTextBean.class.getMethod("add", new Class[] {java.awt.PopupMenu.class}));
            methods[METHOD_add162].setDisplayName ( "" );
            methods[METHOD_remove163] = new MethodDescriptor ( org.htmlparser.beans.HTMLTextBean.class.getMethod("remove", new Class[] {java.awt.MenuComponent.class}));
            methods[METHOD_remove163].setDisplayName ( "" );
            methods[METHOD_toString164] = new MethodDescriptor ( org.htmlparser.beans.HTMLTextBean.class.getMethod("toString", new Class[] {}));
            methods[METHOD_toString164].setDisplayName ( "" );
            methods[METHOD_list165] = new MethodDescriptor ( org.htmlparser.beans.HTMLTextBean.class.getMethod("list", new Class[] {}));
            methods[METHOD_list165].setDisplayName ( "" );
            methods[METHOD_list166] = new MethodDescriptor ( org.htmlparser.beans.HTMLTextBean.class.getMethod("list", new Class[] {java.io.PrintStream.class}));
            methods[METHOD_list166].setDisplayName ( "" );
            methods[METHOD_list167] = new MethodDescriptor ( org.htmlparser.beans.HTMLTextBean.class.getMethod("list", new Class[] {java.io.PrintWriter.class}));
            methods[METHOD_list167].setDisplayName ( "" );
        }
        catch( Exception e) {}//GEN-HEADEREND:Methods
        
        // Here you can add code for customizing the methods array.
        
        return methods;         }//GEN-LAST:Methods
    
    private static java.awt.Image iconColor16 = null; //GEN-BEGIN:IconsDef
    private static java.awt.Image iconColor32 = null;
    private static java.awt.Image iconMono16 = null;
    private static java.awt.Image iconMono32 = null; //GEN-END:IconsDef
    private static String iconNameC16 = "images/Knot16.gif";//GEN-BEGIN:Icons
    private static String iconNameC32 = "images/Knot32.gif";
    private static String iconNameM16 = "images/Knot16.gif";
    private static String iconNameM32 = "images/Knot32.gif";//GEN-END:Icons
    
    private static final int defaultPropertyIndex = -1;//GEN-BEGIN:Idx
    private static final int defaultEventIndex = -1;//GEN-END:Idx
    
    
 //GEN-FIRST:Superclass
    
    // Here you can add code for customizing the Superclass BeanInfo.
    
 //GEN-LAST:Superclass
    
    /**
     * Gets the bean's <code>BeanDescriptor</code>s.
     *
     * @return BeanDescriptor describing the editable
     * properties of this bean.  May return null if the
     * information should be obtained by automatic analysis.
     */
    public BeanDescriptor getBeanDescriptor ()
    {
        return getBdescriptor ();
    }
    
    /**
     * Gets the bean's <code>PropertyDescriptor</code>s.
     *
     * @return An array of PropertyDescriptors describing the editable
     * properties supported by this bean.  May return null if the
     * information should be obtained by automatic analysis.
     * <p>
     * If a property is indexed, then its entry in the result array will
     * belong to the IndexedPropertyDescriptor subclass of PropertyDescriptor.
     * A client of getPropertyDescriptors can use "instanceof" to check
     * if a given PropertyDescriptor is an IndexedPropertyDescriptor.
     */
    public PropertyDescriptor[] getPropertyDescriptors ()
    {
        return getPdescriptor ();
    }
    
    /**
     * Gets the bean's <code>EventSetDescriptor</code>s.
     *
     * @return  An array of EventSetDescriptors describing the kinds of
     * events fired by this bean.  May return null if the information
     * should be obtained by automatic analysis.
     */
    public EventSetDescriptor[] getEventSetDescriptors ()
    {
        return getEdescriptor ();
    }
    
    /**
     * Gets the bean's <code>MethodDescriptor</code>s.
     *
     * @return  An array of MethodDescriptors describing the methods
     * implemented by this bean.  May return null if the information
     * should be obtained by automatic analysis.
     */
    public MethodDescriptor[] getMethodDescriptors ()
    {
        return getMdescriptor ();
    }
    
    /**
     * A bean may have a "default" property that is the property that will
     * mostly commonly be initially chosen for update by human's who are
     * customizing the bean.
     * @return  Index of default property in the PropertyDescriptor array
     * 		returned by getPropertyDescriptors.
     * <P>	Returns -1 if there is no default property.
     */
    public int getDefaultPropertyIndex ()
    {
        return defaultPropertyIndex;
    }
    
    /**
     * A bean may have a "default" event that is the event that will
     * mostly commonly be used by human's when using the bean.
     * @return Index of default event in the EventSetDescriptor array
     *		returned by getEventSetDescriptors.
     * <P>	Returns -1 if there is no default event.
     */
    public int getDefaultEventIndex ()
    {
        return defaultEventIndex;
    }
    
    /**
     * This method returns an image object that can be used to
     * represent the bean in toolboxes, toolbars, etc.   Icon images
     * will typically be GIFs, but may in future include other formats.
     * <p>
     * Beans aren't required to provide icons and may return null from
     * this method.
     * <p>
     * There are four possible flavors of icons (16x16 color,
     * 32x32 color, 16x16 mono, 32x32 mono).  If a bean choses to only
     * support a single icon we recommend supporting 16x16 color.
     * <p>
     * We recommend that icons have a "transparent" background
     * so they can be rendered onto an existing background.
     *
     * @param  iconKind  The kind of icon requested.  This should be
     *    one of the constant values ICON_COLOR_16x16, ICON_COLOR_32x32,
     *    ICON_MONO_16x16, or ICON_MONO_32x32.
     * @return  An image object representing the requested icon.  May
     *    return null if no suitable icon is available.
     */
    public java.awt.Image getIcon (int iconKind)
    {
        switch ( iconKind )
        {
            case ICON_COLOR_16x16:
                if ( iconNameC16 == null )
                    return null;
                else
                {
                    if( iconColor16 == null )
                        iconColor16 = loadImage ( iconNameC16 );
                    return iconColor16;
                }
            case ICON_COLOR_32x32:
                if ( iconNameC32 == null )
                    return null;
                else
                {
                    if( iconColor32 == null )
                        iconColor32 = loadImage ( iconNameC32 );
                    return iconColor32;
                }
            case ICON_MONO_16x16:
                if ( iconNameM16 == null )
                    return null;
                else
                {
                    if( iconMono16 == null )
                        iconMono16 = loadImage ( iconNameM16 );
                    return iconMono16;
                }
            case ICON_MONO_32x32:
                if ( iconNameM32 == null )
                    return null;
                else
                {
                    if( iconMono32 == null )
                        iconMono32 = loadImage ( iconNameM32 );
                    return iconMono32;
                }
            default: return null;
        }
    }
    
}

