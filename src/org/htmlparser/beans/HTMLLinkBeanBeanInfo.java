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
 * Meta information about the HTMLLinkBean.
 */
public class HTMLLinkBeanBeanInfo extends SimpleBeanInfo
{
    
    // Bean descriptor //GEN-FIRST:BeanDescriptor
    /*lazy BeanDescriptor*/
    private static BeanDescriptor getBdescriptor(){
        BeanDescriptor beanDescriptor = new BeanDescriptor  ( HTMLLinkBean.class , null );//GEN-HEADEREND:BeanDescriptor
        
        // Here you can add code for customizing the BeanDescriptor.
        
        return beanDescriptor;         }//GEN-LAST:BeanDescriptor
    
    
    // Property identifiers //GEN-FIRST:Properties
    private static final int PROPERTY_verifyInputWhenFocusTarget = 0;
    private static final int PROPERTY_componentOrientation = 1;
    private static final int PROPERTY_fontSet = 2;
    private static final int PROPERTY_valueIsAdjusting = 3;
    private static final int PROPERTY_locationOnScreen = 4;
    private static final int PROPERTY_mouseWheelListeners = 5;
    private static final int PROPERTY_colorModel = 6;
    private static final int PROPERTY_focusTraversalPolicy = 7;
    private static final int PROPERTY_links = 8;
    private static final int PROPERTY_URL = 9;
    private static final int PROPERTY_registeredKeyStrokes = 10;
    private static final int PROPERTY_scrollableTracksViewportWidth = 11;
    private static final int PROPERTY_alignmentX = 12;
    private static final int PROPERTY_paintingTile = 13;
    private static final int PROPERTY_alignmentY = 14;
    private static final int PROPERTY_hierarchyListeners = 15;
    private static final int PROPERTY_accessibleContext = 16;
    private static final int PROPERTY_preferredSize = 17;
    private static final int PROPERTY_selectedValue = 18;
    private static final int PROPERTY_managingFocus = 19;
    private static final int PROPERTY_minimumSizeSet = 20;
    private static final int PROPERTY_scrollableTracksViewportHeight = 21;
    private static final int PROPERTY_minSelectionIndex = 22;
    private static final int PROPERTY_selectionEmpty = 23;
    private static final int PROPERTY_selectionModel = 24;
    private static final int PROPERTY_focusTraversalPolicySet = 25;
    private static final int PROPERTY_y = 26;
    private static final int PROPERTY_firstVisibleIndex = 27;
    private static final int PROPERTY_x = 28;
    private static final int PROPERTY_visibleRowCount = 29;
    private static final int PROPERTY_cursorSet = 30;
    private static final int PROPERTY_inputMethodRequests = 31;
    private static final int PROPERTY_containerListeners = 32;
    private static final int PROPERTY_insets = 33;
    private static final int PROPERTY_componentCount = 34;
    private static final int PROPERTY_components = 35;
    private static final int PROPERTY_selectionForeground = 36;
    private static final int PROPERTY_selectionMode = 37;
    private static final int PROPERTY_inputVerifier = 38;
    private static final int PROPERTY_listData = 39;
    private static final int PROPERTY_hierarchyBoundsListeners = 40;
    private static final int PROPERTY_border = 41;
    private static final int PROPERTY_name = 42;
    private static final int PROPERTY_optimizedDrawingEnabled = 43;
    private static final int PROPERTY_graphics = 44;
    private static final int PROPERTY_toolTipText = 45;
    private static final int PROPERTY_minimumSize = 46;
    private static final int PROPERTY_focusTraversalKeysEnabled = 47;
    private static final int PROPERTY_foreground = 48;
    private static final int PROPERTY_ignoreRepaint = 49;
    private static final int PROPERTY_focusable = 50;
    private static final int PROPERTY_preferredSizeSet = 51;
    private static final int PROPERTY_visible = 52;
    private static final int PROPERTY_focusCycleRootAncestor = 53;
    private static final int PROPERTY_model = 54;
    private static final int PROPERTY_parent = 55;
    private static final int PROPERTY_rootPane = 56;
    private static final int PROPERTY_prototypeCellValue = 57;
    private static final int PROPERTY_lightweight = 58;
    private static final int PROPERTY_dragEnabled = 59;
    private static final int PROPERTY_width = 60;
    private static final int PROPERTY_keyListeners = 61;
    private static final int PROPERTY_toolkit = 62;
    private static final int PROPERTY_inputContext = 63;
    private static final int PROPERTY_layout = 64;
    private static final int PROPERTY_opaque = 65;
    private static final int PROPERTY_font = 66;
    private static final int PROPERTY_locale = 67;
    private static final int PROPERTY_cursor = 68;
    private static final int PROPERTY_inputMethodListeners = 69;
    private static final int PROPERTY_transferHandler = 70;
    private static final int PROPERTY_vetoableChangeListeners = 71;
    private static final int PROPERTY_layoutOrientation = 72;
    private static final int PROPERTY_doubleBuffered = 73;
    private static final int PROPERTY_visibleRect = 74;
    private static final int PROPERTY_selectedIndices = 75;
    private static final int PROPERTY_maximumSizeSet = 76;
    private static final int PROPERTY_fixedCellWidth = 77;
    private static final int PROPERTY_anchorSelectionIndex = 78;
    private static final int PROPERTY_valid = 79;
    private static final int PROPERTY_focusCycleRoot = 80;
    private static final int PROPERTY_maximumSize = 81;
    private static final int PROPERTY_maxSelectionIndex = 82;
    private static final int PROPERTY_mouseMotionListeners = 83;
    private static final int PROPERTY_bounds = 84;
    private static final int PROPERTY_treeLock = 85;
    private static final int PROPERTY_focusTraversable = 86;
    private static final int PROPERTY_propertyChangeListeners = 87;
    private static final int PROPERTY_autoscrolls = 88;
    private static final int PROPERTY_selectedValues = 89;
    private static final int PROPERTY_componentListeners = 90;
    private static final int PROPERTY_showing = 91;
    private static final int PROPERTY_selectedIndex = 92;
    private static final int PROPERTY_cellRenderer = 93;
    private static final int PROPERTY_dropTarget = 94;
    private static final int PROPERTY_focusListeners = 95;
    private static final int PROPERTY_nextFocusableComponent = 96;
    private static final int PROPERTY_peer = 97;
    private static final int PROPERTY_height = 98;
    private static final int PROPERTY_topLevelAncestor = 99;
    private static final int PROPERTY_displayable = 100;
    private static final int PROPERTY_background = 101;
    private static final int PROPERTY_selectionBackground = 102;
    private static final int PROPERTY_lastVisibleIndex = 103;
    private static final int PROPERTY_graphicsConfiguration = 104;
    private static final int PROPERTY_fixedCellHeight = 105;
    private static final int PROPERTY_focusOwner = 106;
    private static final int PROPERTY_ancestorListeners = 107;
    private static final int PROPERTY_requestFocusEnabled = 108;
    private static final int PROPERTY_debugGraphicsOptions = 109;
    private static final int PROPERTY_backgroundSet = 110;
    private static final int PROPERTY_actionMap = 111;
    private static final int PROPERTY_mouseListeners = 112;
    private static final int PROPERTY_enabled = 113;
    private static final int PROPERTY_foregroundSet = 114;
    private static final int PROPERTY_leadSelectionIndex = 115;
    private static final int PROPERTY_validateRoot = 116;
    private static final int PROPERTY_UI = 117;
    private static final int PROPERTY_listSelectionListeners = 118;
    private static final int PROPERTY_preferredScrollableViewportSize = 119;
    private static final int PROPERTY_UIClassID = 120;
    private static final int PROPERTY_component = 121;
    private static final int PROPERTY_selectionInterval = 122;
    private static final int PROPERTY_focusTraversalKeys = 123;

    // Property array 
    /*lazy PropertyDescriptor*/
    private static PropertyDescriptor[] getPdescriptor(){
        PropertyDescriptor[] properties = new PropertyDescriptor[124];
    
        try {
            properties[PROPERTY_verifyInputWhenFocusTarget] = new PropertyDescriptor ( "verifyInputWhenFocusTarget", HTMLLinkBean.class, "getVerifyInputWhenFocusTarget", "setVerifyInputWhenFocusTarget" );
            properties[PROPERTY_componentOrientation] = new PropertyDescriptor ( "componentOrientation", HTMLLinkBean.class, "getComponentOrientation", "setComponentOrientation" );
            properties[PROPERTY_fontSet] = new PropertyDescriptor ( "fontSet", HTMLLinkBean.class, "isFontSet", null );
            properties[PROPERTY_valueIsAdjusting] = new PropertyDescriptor ( "valueIsAdjusting", HTMLLinkBean.class, "getValueIsAdjusting", "setValueIsAdjusting" );
            properties[PROPERTY_locationOnScreen] = new PropertyDescriptor ( "locationOnScreen", HTMLLinkBean.class, "getLocationOnScreen", null );
            properties[PROPERTY_mouseWheelListeners] = new PropertyDescriptor ( "mouseWheelListeners", HTMLLinkBean.class, "getMouseWheelListeners", null );
            properties[PROPERTY_colorModel] = new PropertyDescriptor ( "colorModel", HTMLLinkBean.class, "getColorModel", null );
            properties[PROPERTY_focusTraversalPolicy] = new PropertyDescriptor ( "focusTraversalPolicy", HTMLLinkBean.class, "getFocusTraversalPolicy", "setFocusTraversalPolicy" );
            properties[PROPERTY_links] = new PropertyDescriptor ( "links", HTMLLinkBean.class, "getLinks", null );
            properties[PROPERTY_URL] = new PropertyDescriptor ( "URL", HTMLLinkBean.class, "getURL", "setURL" );
            properties[PROPERTY_registeredKeyStrokes] = new PropertyDescriptor ( "registeredKeyStrokes", HTMLLinkBean.class, "getRegisteredKeyStrokes", null );
            properties[PROPERTY_scrollableTracksViewportWidth] = new PropertyDescriptor ( "scrollableTracksViewportWidth", HTMLLinkBean.class, "getScrollableTracksViewportWidth", null );
            properties[PROPERTY_alignmentX] = new PropertyDescriptor ( "alignmentX", HTMLLinkBean.class, "getAlignmentX", "setAlignmentX" );
            properties[PROPERTY_paintingTile] = new PropertyDescriptor ( "paintingTile", HTMLLinkBean.class, "isPaintingTile", null );
            properties[PROPERTY_alignmentY] = new PropertyDescriptor ( "alignmentY", HTMLLinkBean.class, "getAlignmentY", "setAlignmentY" );
            properties[PROPERTY_hierarchyListeners] = new PropertyDescriptor ( "hierarchyListeners", HTMLLinkBean.class, "getHierarchyListeners", null );
            properties[PROPERTY_accessibleContext] = new PropertyDescriptor ( "accessibleContext", HTMLLinkBean.class, "getAccessibleContext", null );
            properties[PROPERTY_preferredSize] = new PropertyDescriptor ( "preferredSize", HTMLLinkBean.class, "getPreferredSize", "setPreferredSize" );
            properties[PROPERTY_selectedValue] = new PropertyDescriptor ( "selectedValue", HTMLLinkBean.class, "getSelectedValue", null );
            properties[PROPERTY_managingFocus] = new PropertyDescriptor ( "managingFocus", HTMLLinkBean.class, "isManagingFocus", null );
            properties[PROPERTY_minimumSizeSet] = new PropertyDescriptor ( "minimumSizeSet", HTMLLinkBean.class, "isMinimumSizeSet", null );
            properties[PROPERTY_scrollableTracksViewportHeight] = new PropertyDescriptor ( "scrollableTracksViewportHeight", HTMLLinkBean.class, "getScrollableTracksViewportHeight", null );
            properties[PROPERTY_minSelectionIndex] = new PropertyDescriptor ( "minSelectionIndex", HTMLLinkBean.class, "getMinSelectionIndex", null );
            properties[PROPERTY_selectionEmpty] = new PropertyDescriptor ( "selectionEmpty", HTMLLinkBean.class, "isSelectionEmpty", null );
            properties[PROPERTY_selectionModel] = new PropertyDescriptor ( "selectionModel", HTMLLinkBean.class, "getSelectionModel", "setSelectionModel" );
            properties[PROPERTY_focusTraversalPolicySet] = new PropertyDescriptor ( "focusTraversalPolicySet", HTMLLinkBean.class, "isFocusTraversalPolicySet", null );
            properties[PROPERTY_y] = new PropertyDescriptor ( "y", HTMLLinkBean.class, "getY", null );
            properties[PROPERTY_firstVisibleIndex] = new PropertyDescriptor ( "firstVisibleIndex", HTMLLinkBean.class, "getFirstVisibleIndex", null );
            properties[PROPERTY_x] = new PropertyDescriptor ( "x", HTMLLinkBean.class, "getX", null );
            properties[PROPERTY_visibleRowCount] = new PropertyDescriptor ( "visibleRowCount", HTMLLinkBean.class, "getVisibleRowCount", "setVisibleRowCount" );
            properties[PROPERTY_cursorSet] = new PropertyDescriptor ( "cursorSet", HTMLLinkBean.class, "isCursorSet", null );
            properties[PROPERTY_inputMethodRequests] = new PropertyDescriptor ( "inputMethodRequests", HTMLLinkBean.class, "getInputMethodRequests", null );
            properties[PROPERTY_containerListeners] = new PropertyDescriptor ( "containerListeners", HTMLLinkBean.class, "getContainerListeners", null );
            properties[PROPERTY_insets] = new PropertyDescriptor ( "insets", HTMLLinkBean.class, "getInsets", null );
            properties[PROPERTY_componentCount] = new PropertyDescriptor ( "componentCount", HTMLLinkBean.class, "getComponentCount", null );
            properties[PROPERTY_components] = new PropertyDescriptor ( "components", HTMLLinkBean.class, "getComponents", null );
            properties[PROPERTY_selectionForeground] = new PropertyDescriptor ( "selectionForeground", HTMLLinkBean.class, "getSelectionForeground", "setSelectionForeground" );
            properties[PROPERTY_selectionMode] = new PropertyDescriptor ( "selectionMode", HTMLLinkBean.class, "getSelectionMode", "setSelectionMode" );
            properties[PROPERTY_inputVerifier] = new PropertyDescriptor ( "inputVerifier", HTMLLinkBean.class, "getInputVerifier", "setInputVerifier" );
            properties[PROPERTY_listData] = new PropertyDescriptor ( "listData", HTMLLinkBean.class, null, "setListData" );
            properties[PROPERTY_hierarchyBoundsListeners] = new PropertyDescriptor ( "hierarchyBoundsListeners", HTMLLinkBean.class, "getHierarchyBoundsListeners", null );
            properties[PROPERTY_border] = new PropertyDescriptor ( "border", HTMLLinkBean.class, "getBorder", "setBorder" );
            properties[PROPERTY_name] = new PropertyDescriptor ( "name", HTMLLinkBean.class, "getName", "setName" );
            properties[PROPERTY_optimizedDrawingEnabled] = new PropertyDescriptor ( "optimizedDrawingEnabled", HTMLLinkBean.class, "isOptimizedDrawingEnabled", null );
            properties[PROPERTY_graphics] = new PropertyDescriptor ( "graphics", HTMLLinkBean.class, "getGraphics", null );
            properties[PROPERTY_toolTipText] = new PropertyDescriptor ( "toolTipText", HTMLLinkBean.class, "getToolTipText", "setToolTipText" );
            properties[PROPERTY_minimumSize] = new PropertyDescriptor ( "minimumSize", HTMLLinkBean.class, "getMinimumSize", "setMinimumSize" );
            properties[PROPERTY_focusTraversalKeysEnabled] = new PropertyDescriptor ( "focusTraversalKeysEnabled", HTMLLinkBean.class, "getFocusTraversalKeysEnabled", "setFocusTraversalKeysEnabled" );
            properties[PROPERTY_foreground] = new PropertyDescriptor ( "foreground", HTMLLinkBean.class, "getForeground", "setForeground" );
            properties[PROPERTY_ignoreRepaint] = new PropertyDescriptor ( "ignoreRepaint", HTMLLinkBean.class, "getIgnoreRepaint", "setIgnoreRepaint" );
            properties[PROPERTY_focusable] = new PropertyDescriptor ( "focusable", HTMLLinkBean.class, "isFocusable", "setFocusable" );
            properties[PROPERTY_preferredSizeSet] = new PropertyDescriptor ( "preferredSizeSet", HTMLLinkBean.class, "isPreferredSizeSet", null );
            properties[PROPERTY_visible] = new PropertyDescriptor ( "visible", HTMLLinkBean.class, "isVisible", "setVisible" );
            properties[PROPERTY_focusCycleRootAncestor] = new PropertyDescriptor ( "focusCycleRootAncestor", HTMLLinkBean.class, "getFocusCycleRootAncestor", null );
            properties[PROPERTY_model] = new PropertyDescriptor ( "model", HTMLLinkBean.class, "getModel", "setModel" );
            properties[PROPERTY_parent] = new PropertyDescriptor ( "parent", HTMLLinkBean.class, "getParent", null );
            properties[PROPERTY_rootPane] = new PropertyDescriptor ( "rootPane", HTMLLinkBean.class, "getRootPane", null );
            properties[PROPERTY_prototypeCellValue] = new PropertyDescriptor ( "prototypeCellValue", HTMLLinkBean.class, "getPrototypeCellValue", "setPrototypeCellValue" );
            properties[PROPERTY_lightweight] = new PropertyDescriptor ( "lightweight", HTMLLinkBean.class, "isLightweight", null );
            properties[PROPERTY_dragEnabled] = new PropertyDescriptor ( "dragEnabled", HTMLLinkBean.class, "getDragEnabled", "setDragEnabled" );
            properties[PROPERTY_width] = new PropertyDescriptor ( "width", HTMLLinkBean.class, "getWidth", null );
            properties[PROPERTY_keyListeners] = new PropertyDescriptor ( "keyListeners", HTMLLinkBean.class, "getKeyListeners", null );
            properties[PROPERTY_toolkit] = new PropertyDescriptor ( "toolkit", HTMLLinkBean.class, "getToolkit", null );
            properties[PROPERTY_inputContext] = new PropertyDescriptor ( "inputContext", HTMLLinkBean.class, "getInputContext", null );
            properties[PROPERTY_layout] = new PropertyDescriptor ( "layout", HTMLLinkBean.class, "getLayout", "setLayout" );
            properties[PROPERTY_opaque] = new PropertyDescriptor ( "opaque", HTMLLinkBean.class, "isOpaque", "setOpaque" );
            properties[PROPERTY_font] = new PropertyDescriptor ( "font", HTMLLinkBean.class, "getFont", "setFont" );
            properties[PROPERTY_locale] = new PropertyDescriptor ( "locale", HTMLLinkBean.class, "getLocale", "setLocale" );
            properties[PROPERTY_cursor] = new PropertyDescriptor ( "cursor", HTMLLinkBean.class, "getCursor", "setCursor" );
            properties[PROPERTY_inputMethodListeners] = new PropertyDescriptor ( "inputMethodListeners", HTMLLinkBean.class, "getInputMethodListeners", null );
            properties[PROPERTY_transferHandler] = new PropertyDescriptor ( "transferHandler", HTMLLinkBean.class, "getTransferHandler", "setTransferHandler" );
            properties[PROPERTY_vetoableChangeListeners] = new PropertyDescriptor ( "vetoableChangeListeners", HTMLLinkBean.class, "getVetoableChangeListeners", null );
            properties[PROPERTY_layoutOrientation] = new PropertyDescriptor ( "layoutOrientation", HTMLLinkBean.class, "getLayoutOrientation", "setLayoutOrientation" );
            properties[PROPERTY_doubleBuffered] = new PropertyDescriptor ( "doubleBuffered", HTMLLinkBean.class, "isDoubleBuffered", "setDoubleBuffered" );
            properties[PROPERTY_visibleRect] = new PropertyDescriptor ( "visibleRect", HTMLLinkBean.class, "getVisibleRect", null );
            properties[PROPERTY_selectedIndices] = new PropertyDescriptor ( "selectedIndices", HTMLLinkBean.class, "getSelectedIndices", "setSelectedIndices" );
            properties[PROPERTY_maximumSizeSet] = new PropertyDescriptor ( "maximumSizeSet", HTMLLinkBean.class, "isMaximumSizeSet", null );
            properties[PROPERTY_fixedCellWidth] = new PropertyDescriptor ( "fixedCellWidth", HTMLLinkBean.class, "getFixedCellWidth", "setFixedCellWidth" );
            properties[PROPERTY_anchorSelectionIndex] = new PropertyDescriptor ( "anchorSelectionIndex", HTMLLinkBean.class, "getAnchorSelectionIndex", null );
            properties[PROPERTY_valid] = new PropertyDescriptor ( "valid", HTMLLinkBean.class, "isValid", null );
            properties[PROPERTY_focusCycleRoot] = new PropertyDescriptor ( "focusCycleRoot", HTMLLinkBean.class, "isFocusCycleRoot", "setFocusCycleRoot" );
            properties[PROPERTY_maximumSize] = new PropertyDescriptor ( "maximumSize", HTMLLinkBean.class, "getMaximumSize", "setMaximumSize" );
            properties[PROPERTY_maxSelectionIndex] = new PropertyDescriptor ( "maxSelectionIndex", HTMLLinkBean.class, "getMaxSelectionIndex", null );
            properties[PROPERTY_mouseMotionListeners] = new PropertyDescriptor ( "mouseMotionListeners", HTMLLinkBean.class, "getMouseMotionListeners", null );
            properties[PROPERTY_bounds] = new PropertyDescriptor ( "bounds", HTMLLinkBean.class, "getBounds", "setBounds" );
            properties[PROPERTY_treeLock] = new PropertyDescriptor ( "treeLock", HTMLLinkBean.class, "getTreeLock", null );
            properties[PROPERTY_focusTraversable] = new PropertyDescriptor ( "focusTraversable", HTMLLinkBean.class, "isFocusTraversable", null );
            properties[PROPERTY_propertyChangeListeners] = new PropertyDescriptor ( "propertyChangeListeners", HTMLLinkBean.class, "getPropertyChangeListeners", null );
            properties[PROPERTY_autoscrolls] = new PropertyDescriptor ( "autoscrolls", HTMLLinkBean.class, "getAutoscrolls", "setAutoscrolls" );
            properties[PROPERTY_selectedValues] = new PropertyDescriptor ( "selectedValues", HTMLLinkBean.class, "getSelectedValues", null );
            properties[PROPERTY_componentListeners] = new PropertyDescriptor ( "componentListeners", HTMLLinkBean.class, "getComponentListeners", null );
            properties[PROPERTY_showing] = new PropertyDescriptor ( "showing", HTMLLinkBean.class, "isShowing", null );
            properties[PROPERTY_selectedIndex] = new PropertyDescriptor ( "selectedIndex", HTMLLinkBean.class, "getSelectedIndex", "setSelectedIndex" );
            properties[PROPERTY_cellRenderer] = new PropertyDescriptor ( "cellRenderer", HTMLLinkBean.class, "getCellRenderer", "setCellRenderer" );
            properties[PROPERTY_dropTarget] = new PropertyDescriptor ( "dropTarget", HTMLLinkBean.class, "getDropTarget", "setDropTarget" );
            properties[PROPERTY_focusListeners] = new PropertyDescriptor ( "focusListeners", HTMLLinkBean.class, "getFocusListeners", null );
            properties[PROPERTY_nextFocusableComponent] = new PropertyDescriptor ( "nextFocusableComponent", HTMLLinkBean.class, "getNextFocusableComponent", "setNextFocusableComponent" );
            properties[PROPERTY_peer] = new PropertyDescriptor ( "peer", HTMLLinkBean.class, "getPeer", null );
            properties[PROPERTY_height] = new PropertyDescriptor ( "height", HTMLLinkBean.class, "getHeight", null );
            properties[PROPERTY_topLevelAncestor] = new PropertyDescriptor ( "topLevelAncestor", HTMLLinkBean.class, "getTopLevelAncestor", null );
            properties[PROPERTY_displayable] = new PropertyDescriptor ( "displayable", HTMLLinkBean.class, "isDisplayable", null );
            properties[PROPERTY_background] = new PropertyDescriptor ( "background", HTMLLinkBean.class, "getBackground", "setBackground" );
            properties[PROPERTY_selectionBackground] = new PropertyDescriptor ( "selectionBackground", HTMLLinkBean.class, "getSelectionBackground", "setSelectionBackground" );
            properties[PROPERTY_lastVisibleIndex] = new PropertyDescriptor ( "lastVisibleIndex", HTMLLinkBean.class, "getLastVisibleIndex", null );
            properties[PROPERTY_graphicsConfiguration] = new PropertyDescriptor ( "graphicsConfiguration", HTMLLinkBean.class, "getGraphicsConfiguration", null );
            properties[PROPERTY_fixedCellHeight] = new PropertyDescriptor ( "fixedCellHeight", HTMLLinkBean.class, "getFixedCellHeight", "setFixedCellHeight" );
            properties[PROPERTY_focusOwner] = new PropertyDescriptor ( "focusOwner", HTMLLinkBean.class, "isFocusOwner", null );
            properties[PROPERTY_ancestorListeners] = new PropertyDescriptor ( "ancestorListeners", HTMLLinkBean.class, "getAncestorListeners", null );
            properties[PROPERTY_requestFocusEnabled] = new PropertyDescriptor ( "requestFocusEnabled", HTMLLinkBean.class, "isRequestFocusEnabled", "setRequestFocusEnabled" );
            properties[PROPERTY_debugGraphicsOptions] = new PropertyDescriptor ( "debugGraphicsOptions", HTMLLinkBean.class, "getDebugGraphicsOptions", "setDebugGraphicsOptions" );
            properties[PROPERTY_backgroundSet] = new PropertyDescriptor ( "backgroundSet", HTMLLinkBean.class, "isBackgroundSet", null );
            properties[PROPERTY_actionMap] = new PropertyDescriptor ( "actionMap", HTMLLinkBean.class, "getActionMap", "setActionMap" );
            properties[PROPERTY_mouseListeners] = new PropertyDescriptor ( "mouseListeners", HTMLLinkBean.class, "getMouseListeners", null );
            properties[PROPERTY_enabled] = new PropertyDescriptor ( "enabled", HTMLLinkBean.class, "isEnabled", "setEnabled" );
            properties[PROPERTY_foregroundSet] = new PropertyDescriptor ( "foregroundSet", HTMLLinkBean.class, "isForegroundSet", null );
            properties[PROPERTY_leadSelectionIndex] = new PropertyDescriptor ( "leadSelectionIndex", HTMLLinkBean.class, "getLeadSelectionIndex", null );
            properties[PROPERTY_validateRoot] = new PropertyDescriptor ( "validateRoot", HTMLLinkBean.class, "isValidateRoot", null );
            properties[PROPERTY_UI] = new PropertyDescriptor ( "UI", HTMLLinkBean.class, "getUI", "setUI" );
            properties[PROPERTY_listSelectionListeners] = new PropertyDescriptor ( "listSelectionListeners", HTMLLinkBean.class, "getListSelectionListeners", null );
            properties[PROPERTY_preferredScrollableViewportSize] = new PropertyDescriptor ( "preferredScrollableViewportSize", HTMLLinkBean.class, "getPreferredScrollableViewportSize", null );
            properties[PROPERTY_UIClassID] = new PropertyDescriptor ( "UIClassID", HTMLLinkBean.class, "getUIClassID", null );
            properties[PROPERTY_component] = new IndexedPropertyDescriptor ( "component", HTMLLinkBean.class, null, null, "getComponent", null );
            properties[PROPERTY_selectionInterval] = new IndexedPropertyDescriptor ( "selectionInterval", HTMLLinkBean.class, null, null, null, "setSelectionInterval" );
            properties[PROPERTY_focusTraversalKeys] = new IndexedPropertyDescriptor ( "focusTraversalKeys", HTMLLinkBean.class, null, null, "getFocusTraversalKeys", "setFocusTraversalKeys" );
        }
        catch( IntrospectionException e) {}//GEN-HEADEREND:Properties
        
        // Here you can add code for customizing the properties array.
        
        return properties;         }//GEN-LAST:Properties
    
    // EventSet identifiers//GEN-FIRST:Events
    private static final int EVENT_inputMethodListener = 0;
    private static final int EVENT_containerListener = 1;
    private static final int EVENT_mouseMotionListener = 2;
    private static final int EVENT_mouseWheelListener = 3;
    private static final int EVENT_mouseListener = 4;
    private static final int EVENT_componentListener = 5;
    private static final int EVENT_hierarchyBoundsListener = 6;
    private static final int EVENT_listSelectionListener = 7;
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
            eventSets[EVENT_inputMethodListener] = new EventSetDescriptor ( org.htmlparser.beans.HTMLLinkBean.class, "inputMethodListener", java.awt.event.InputMethodListener.class, new String[] {"inputMethodTextChanged", "caretPositionChanged"}, "addInputMethodListener", "removeInputMethodListener" );
            eventSets[EVENT_containerListener] = new EventSetDescriptor ( org.htmlparser.beans.HTMLLinkBean.class, "containerListener", java.awt.event.ContainerListener.class, new String[] {"componentAdded", "componentRemoved"}, "addContainerListener", "removeContainerListener" );
            eventSets[EVENT_mouseMotionListener] = new EventSetDescriptor ( org.htmlparser.beans.HTMLLinkBean.class, "mouseMotionListener", java.awt.event.MouseMotionListener.class, new String[] {"mouseDragged", "mouseMoved"}, "addMouseMotionListener", "removeMouseMotionListener" );
            eventSets[EVENT_mouseWheelListener] = new EventSetDescriptor ( org.htmlparser.beans.HTMLLinkBean.class, "mouseWheelListener", java.awt.event.MouseWheelListener.class, new String[] {"mouseWheelMoved"}, "addMouseWheelListener", "removeMouseWheelListener" );
            eventSets[EVENT_mouseListener] = new EventSetDescriptor ( org.htmlparser.beans.HTMLLinkBean.class, "mouseListener", java.awt.event.MouseListener.class, new String[] {"mouseClicked", "mousePressed", "mouseReleased", "mouseEntered", "mouseExited"}, "addMouseListener", "removeMouseListener" );
            eventSets[EVENT_componentListener] = new EventSetDescriptor ( org.htmlparser.beans.HTMLLinkBean.class, "componentListener", java.awt.event.ComponentListener.class, new String[] {"componentResized", "componentMoved", "componentShown", "componentHidden"}, "addComponentListener", "removeComponentListener" );
            eventSets[EVENT_hierarchyBoundsListener] = new EventSetDescriptor ( org.htmlparser.beans.HTMLLinkBean.class, "hierarchyBoundsListener", java.awt.event.HierarchyBoundsListener.class, new String[] {"ancestorMoved", "ancestorResized"}, "addHierarchyBoundsListener", "removeHierarchyBoundsListener" );
            eventSets[EVENT_listSelectionListener] = new EventSetDescriptor ( org.htmlparser.beans.HTMLLinkBean.class, "listSelectionListener", javax.swing.event.ListSelectionListener.class, new String[] {"valueChanged"}, "addListSelectionListener", "removeListSelectionListener" );
            eventSets[EVENT_ancestorListener] = new EventSetDescriptor ( org.htmlparser.beans.HTMLLinkBean.class, "ancestorListener", javax.swing.event.AncestorListener.class, new String[] {"ancestorAdded", "ancestorRemoved", "ancestorMoved"}, "addAncestorListener", "removeAncestorListener" );
            eventSets[EVENT_focusListener] = new EventSetDescriptor ( org.htmlparser.beans.HTMLLinkBean.class, "focusListener", java.awt.event.FocusListener.class, new String[] {"focusGained", "focusLost"}, "addFocusListener", "removeFocusListener" );
            eventSets[EVENT_keyListener] = new EventSetDescriptor ( org.htmlparser.beans.HTMLLinkBean.class, "keyListener", java.awt.event.KeyListener.class, new String[] {"keyTyped", "keyPressed", "keyReleased"}, "addKeyListener", "removeKeyListener" );
            eventSets[EVENT_hierarchyListener] = new EventSetDescriptor ( org.htmlparser.beans.HTMLLinkBean.class, "hierarchyListener", java.awt.event.HierarchyListener.class, new String[] {"hierarchyChanged"}, "addHierarchyListener", "removeHierarchyListener" );
            eventSets[EVENT_vetoableChangeListener] = new EventSetDescriptor ( org.htmlparser.beans.HTMLLinkBean.class, "vetoableChangeListener", java.beans.VetoableChangeListener.class, new String[] {"vetoableChange"}, "addVetoableChangeListener", "removeVetoableChangeListener" );
            eventSets[EVENT_propertyChangeListener] = new EventSetDescriptor ( org.htmlparser.beans.HTMLLinkBean.class, "propertyChangeListener", java.beans.PropertyChangeListener.class, new String[] {"propertyChange"}, "addPropertyChangeListener", "removePropertyChangeListener" );
        }
        catch( IntrospectionException e) {}//GEN-HEADEREND:Events
        
        // Here you can add code for customizing the event sets array.
        
        return eventSets;         }//GEN-LAST:Events
    
    // Method identifiers //GEN-FIRST:Methods
    private static final int METHOD_propertyChange0 = 0;
    private static final int METHOD_updateUI1 = 1;
    private static final int METHOD_ensureIndexIsVisible2 = 2;
    private static final int METHOD_getNextMatch3 = 3;
    private static final int METHOD_getToolTipText4 = 4;
    private static final int METHOD_locationToIndex5 = 5;
    private static final int METHOD_indexToLocation6 = 6;
    private static final int METHOD_getCellBounds7 = 7;
    private static final int METHOD_setListData8 = 8;
    private static final int METHOD_isSelectedIndex9 = 9;
    private static final int METHOD_clearSelection10 = 10;
    private static final int METHOD_addSelectionInterval11 = 11;
    private static final int METHOD_removeSelectionInterval12 = 12;
    private static final int METHOD_setSelectedValue13 = 13;
    private static final int METHOD_getScrollableUnitIncrement14 = 14;
    private static final int METHOD_getScrollableBlockIncrement15 = 15;
    private static final int METHOD_update16 = 16;
    private static final int METHOD_paint17 = 17;
    private static final int METHOD_printAll18 = 18;
    private static final int METHOD_print19 = 19;
    private static final int METHOD_requestFocus20 = 20;
    private static final int METHOD_requestFocus21 = 21;
    private static final int METHOD_requestFocusInWindow22 = 22;
    private static final int METHOD_grabFocus23 = 23;
    private static final int METHOD_contains24 = 24;
    private static final int METHOD_getInsets25 = 25;
    private static final int METHOD_registerKeyboardAction26 = 26;
    private static final int METHOD_registerKeyboardAction27 = 27;
    private static final int METHOD_unregisterKeyboardAction28 = 28;
    private static final int METHOD_getConditionForKeyStroke29 = 29;
    private static final int METHOD_getActionForKeyStroke30 = 30;
    private static final int METHOD_resetKeyboardActions31 = 31;
    private static final int METHOD_setInputMap32 = 32;
    private static final int METHOD_getInputMap33 = 33;
    private static final int METHOD_getInputMap34 = 34;
    private static final int METHOD_requestDefaultFocus35 = 35;
    private static final int METHOD_getDefaultLocale36 = 36;
    private static final int METHOD_setDefaultLocale37 = 37;
    private static final int METHOD_getToolTipLocation38 = 38;
    private static final int METHOD_createToolTip39 = 39;
    private static final int METHOD_scrollRectToVisible40 = 40;
    private static final int METHOD_enable41 = 41;
    private static final int METHOD_disable42 = 42;
    private static final int METHOD_getClientProperty43 = 43;
    private static final int METHOD_putClientProperty44 = 44;
    private static final int METHOD_isLightweightComponent45 = 45;
    private static final int METHOD_reshape46 = 46;
    private static final int METHOD_getBounds47 = 47;
    private static final int METHOD_getSize48 = 48;
    private static final int METHOD_getLocation49 = 49;
    private static final int METHOD_computeVisibleRect50 = 50;
    private static final int METHOD_firePropertyChange51 = 51;
    private static final int METHOD_firePropertyChange52 = 52;
    private static final int METHOD_firePropertyChange53 = 53;
    private static final int METHOD_firePropertyChange54 = 54;
    private static final int METHOD_firePropertyChange55 = 55;
    private static final int METHOD_firePropertyChange56 = 56;
    private static final int METHOD_firePropertyChange57 = 57;
    private static final int METHOD_firePropertyChange58 = 58;
    private static final int METHOD_addPropertyChangeListener59 = 59;
    private static final int METHOD_removePropertyChangeListener60 = 60;
    private static final int METHOD_getPropertyChangeListeners61 = 61;
    private static final int METHOD_getListeners62 = 62;
    private static final int METHOD_addNotify63 = 63;
    private static final int METHOD_removeNotify64 = 64;
    private static final int METHOD_repaint65 = 65;
    private static final int METHOD_repaint66 = 66;
    private static final int METHOD_revalidate67 = 67;
    private static final int METHOD_paintImmediately68 = 68;
    private static final int METHOD_paintImmediately69 = 69;
    private static final int METHOD_countComponents70 = 70;
    private static final int METHOD_insets71 = 71;
    private static final int METHOD_add72 = 72;
    private static final int METHOD_add73 = 73;
    private static final int METHOD_add74 = 74;
    private static final int METHOD_add75 = 75;
    private static final int METHOD_add76 = 76;
    private static final int METHOD_remove77 = 77;
    private static final int METHOD_remove78 = 78;
    private static final int METHOD_removeAll79 = 79;
    private static final int METHOD_doLayout80 = 80;
    private static final int METHOD_layout81 = 81;
    private static final int METHOD_invalidate82 = 82;
    private static final int METHOD_validate83 = 83;
    private static final int METHOD_preferredSize84 = 84;
    private static final int METHOD_minimumSize85 = 85;
    private static final int METHOD_paintComponents86 = 86;
    private static final int METHOD_printComponents87 = 87;
    private static final int METHOD_deliverEvent88 = 88;
    private static final int METHOD_getComponentAt89 = 89;
    private static final int METHOD_locate90 = 90;
    private static final int METHOD_getComponentAt91 = 91;
    private static final int METHOD_findComponentAt92 = 92;
    private static final int METHOD_findComponentAt93 = 93;
    private static final int METHOD_isAncestorOf94 = 94;
    private static final int METHOD_list95 = 95;
    private static final int METHOD_list96 = 96;
    private static final int METHOD_areFocusTraversalKeysSet97 = 97;
    private static final int METHOD_isFocusCycleRoot98 = 98;
    private static final int METHOD_transferFocusBackward99 = 99;
    private static final int METHOD_transferFocusDownCycle100 = 100;
    private static final int METHOD_applyComponentOrientation101 = 101;
    private static final int METHOD_enable102 = 102;
    private static final int METHOD_enableInputMethods103 = 103;
    private static final int METHOD_show104 = 104;
    private static final int METHOD_show105 = 105;
    private static final int METHOD_hide106 = 106;
    private static final int METHOD_getLocation107 = 107;
    private static final int METHOD_location108 = 108;
    private static final int METHOD_setLocation109 = 109;
    private static final int METHOD_move110 = 110;
    private static final int METHOD_setLocation111 = 111;
    private static final int METHOD_getSize112 = 112;
    private static final int METHOD_size113 = 113;
    private static final int METHOD_setSize114 = 114;
    private static final int METHOD_resize115 = 115;
    private static final int METHOD_setSize116 = 116;
    private static final int METHOD_resize117 = 117;
    private static final int METHOD_bounds118 = 118;
    private static final int METHOD_setBounds119 = 119;
    private static final int METHOD_getFontMetrics120 = 120;
    private static final int METHOD_paintAll121 = 121;
    private static final int METHOD_repaint122 = 122;
    private static final int METHOD_repaint123 = 123;
    private static final int METHOD_repaint124 = 124;
    private static final int METHOD_imageUpdate125 = 125;
    private static final int METHOD_createImage126 = 126;
    private static final int METHOD_createImage127 = 127;
    private static final int METHOD_createVolatileImage128 = 128;
    private static final int METHOD_createVolatileImage129 = 129;
    private static final int METHOD_prepareImage130 = 130;
    private static final int METHOD_prepareImage131 = 131;
    private static final int METHOD_checkImage132 = 132;
    private static final int METHOD_checkImage133 = 133;
    private static final int METHOD_inside134 = 134;
    private static final int METHOD_contains135 = 135;
    private static final int METHOD_dispatchEvent136 = 136;
    private static final int METHOD_postEvent137 = 137;
    private static final int METHOD_handleEvent138 = 138;
    private static final int METHOD_mouseDown139 = 139;
    private static final int METHOD_mouseDrag140 = 140;
    private static final int METHOD_mouseUp141 = 141;
    private static final int METHOD_mouseMove142 = 142;
    private static final int METHOD_mouseEnter143 = 143;
    private static final int METHOD_mouseExit144 = 144;
    private static final int METHOD_keyDown145 = 145;
    private static final int METHOD_keyUp146 = 146;
    private static final int METHOD_action147 = 147;
    private static final int METHOD_gotFocus148 = 148;
    private static final int METHOD_lostFocus149 = 149;
    private static final int METHOD_transferFocus150 = 150;
    private static final int METHOD_nextFocus151 = 151;
    private static final int METHOD_transferFocusUpCycle152 = 152;
    private static final int METHOD_hasFocus153 = 153;
    private static final int METHOD_add154 = 154;
    private static final int METHOD_remove155 = 155;
    private static final int METHOD_toString156 = 156;
    private static final int METHOD_list157 = 157;
    private static final int METHOD_list158 = 158;
    private static final int METHOD_list159 = 159;

    // Method array 
    /*lazy MethodDescriptor*/
    private static MethodDescriptor[] getMdescriptor(){
        MethodDescriptor[] methods = new MethodDescriptor[160];
    
        try {
            methods[METHOD_propertyChange0] = new MethodDescriptor ( org.htmlparser.beans.HTMLLinkBean.class.getMethod("propertyChange", new Class[] {java.beans.PropertyChangeEvent.class}));
            methods[METHOD_propertyChange0].setDisplayName ( "" );
            methods[METHOD_updateUI1] = new MethodDescriptor ( org.htmlparser.beans.HTMLLinkBean.class.getMethod("updateUI", new Class[] {}));
            methods[METHOD_updateUI1].setDisplayName ( "" );
            methods[METHOD_ensureIndexIsVisible2] = new MethodDescriptor ( org.htmlparser.beans.HTMLLinkBean.class.getMethod("ensureIndexIsVisible", new Class[] {Integer.TYPE}));
            methods[METHOD_ensureIndexIsVisible2].setDisplayName ( "" );
            methods[METHOD_getNextMatch3] = new MethodDescriptor ( org.htmlparser.beans.HTMLLinkBean.class.getMethod("getNextMatch", new Class[] {java.lang.String.class, Integer.TYPE, javax.swing.text.Position.Bias.class}));
            methods[METHOD_getNextMatch3].setDisplayName ( "" );
            methods[METHOD_getToolTipText4] = new MethodDescriptor ( org.htmlparser.beans.HTMLLinkBean.class.getMethod("getToolTipText", new Class[] {java.awt.event.MouseEvent.class}));
            methods[METHOD_getToolTipText4].setDisplayName ( "" );
            methods[METHOD_locationToIndex5] = new MethodDescriptor ( org.htmlparser.beans.HTMLLinkBean.class.getMethod("locationToIndex", new Class[] {java.awt.Point.class}));
            methods[METHOD_locationToIndex5].setDisplayName ( "" );
            methods[METHOD_indexToLocation6] = new MethodDescriptor ( org.htmlparser.beans.HTMLLinkBean.class.getMethod("indexToLocation", new Class[] {Integer.TYPE}));
            methods[METHOD_indexToLocation6].setDisplayName ( "" );
            methods[METHOD_getCellBounds7] = new MethodDescriptor ( org.htmlparser.beans.HTMLLinkBean.class.getMethod("getCellBounds", new Class[] {Integer.TYPE, Integer.TYPE}));
            methods[METHOD_getCellBounds7].setDisplayName ( "" );
            methods[METHOD_setListData8] = new MethodDescriptor ( org.htmlparser.beans.HTMLLinkBean.class.getMethod("setListData", new Class[] {Class.forName("[Ljava.lang.Object;")}));
            methods[METHOD_setListData8].setDisplayName ( "" );
            methods[METHOD_isSelectedIndex9] = new MethodDescriptor ( org.htmlparser.beans.HTMLLinkBean.class.getMethod("isSelectedIndex", new Class[] {Integer.TYPE}));
            methods[METHOD_isSelectedIndex9].setDisplayName ( "" );
            methods[METHOD_clearSelection10] = new MethodDescriptor ( org.htmlparser.beans.HTMLLinkBean.class.getMethod("clearSelection", new Class[] {}));
            methods[METHOD_clearSelection10].setDisplayName ( "" );
            methods[METHOD_addSelectionInterval11] = new MethodDescriptor ( org.htmlparser.beans.HTMLLinkBean.class.getMethod("addSelectionInterval", new Class[] {Integer.TYPE, Integer.TYPE}));
            methods[METHOD_addSelectionInterval11].setDisplayName ( "" );
            methods[METHOD_removeSelectionInterval12] = new MethodDescriptor ( org.htmlparser.beans.HTMLLinkBean.class.getMethod("removeSelectionInterval", new Class[] {Integer.TYPE, Integer.TYPE}));
            methods[METHOD_removeSelectionInterval12].setDisplayName ( "" );
            methods[METHOD_setSelectedValue13] = new MethodDescriptor ( org.htmlparser.beans.HTMLLinkBean.class.getMethod("setSelectedValue", new Class[] {java.lang.Object.class, Boolean.TYPE}));
            methods[METHOD_setSelectedValue13].setDisplayName ( "" );
            methods[METHOD_getScrollableUnitIncrement14] = new MethodDescriptor ( org.htmlparser.beans.HTMLLinkBean.class.getMethod("getScrollableUnitIncrement", new Class[] {java.awt.Rectangle.class, Integer.TYPE, Integer.TYPE}));
            methods[METHOD_getScrollableUnitIncrement14].setDisplayName ( "" );
            methods[METHOD_getScrollableBlockIncrement15] = new MethodDescriptor ( org.htmlparser.beans.HTMLLinkBean.class.getMethod("getScrollableBlockIncrement", new Class[] {java.awt.Rectangle.class, Integer.TYPE, Integer.TYPE}));
            methods[METHOD_getScrollableBlockIncrement15].setDisplayName ( "" );
            methods[METHOD_update16] = new MethodDescriptor ( org.htmlparser.beans.HTMLLinkBean.class.getMethod("update", new Class[] {java.awt.Graphics.class}));
            methods[METHOD_update16].setDisplayName ( "" );
            methods[METHOD_paint17] = new MethodDescriptor ( org.htmlparser.beans.HTMLLinkBean.class.getMethod("paint", new Class[] {java.awt.Graphics.class}));
            methods[METHOD_paint17].setDisplayName ( "" );
            methods[METHOD_printAll18] = new MethodDescriptor ( org.htmlparser.beans.HTMLLinkBean.class.getMethod("printAll", new Class[] {java.awt.Graphics.class}));
            methods[METHOD_printAll18].setDisplayName ( "" );
            methods[METHOD_print19] = new MethodDescriptor ( org.htmlparser.beans.HTMLLinkBean.class.getMethod("print", new Class[] {java.awt.Graphics.class}));
            methods[METHOD_print19].setDisplayName ( "" );
            methods[METHOD_requestFocus20] = new MethodDescriptor ( org.htmlparser.beans.HTMLLinkBean.class.getMethod("requestFocus", new Class[] {}));
            methods[METHOD_requestFocus20].setDisplayName ( "" );
            methods[METHOD_requestFocus21] = new MethodDescriptor ( org.htmlparser.beans.HTMLLinkBean.class.getMethod("requestFocus", new Class[] {Boolean.TYPE}));
            methods[METHOD_requestFocus21].setDisplayName ( "" );
            methods[METHOD_requestFocusInWindow22] = new MethodDescriptor ( org.htmlparser.beans.HTMLLinkBean.class.getMethod("requestFocusInWindow", new Class[] {}));
            methods[METHOD_requestFocusInWindow22].setDisplayName ( "" );
            methods[METHOD_grabFocus23] = new MethodDescriptor ( org.htmlparser.beans.HTMLLinkBean.class.getMethod("grabFocus", new Class[] {}));
            methods[METHOD_grabFocus23].setDisplayName ( "" );
            methods[METHOD_contains24] = new MethodDescriptor ( org.htmlparser.beans.HTMLLinkBean.class.getMethod("contains", new Class[] {Integer.TYPE, Integer.TYPE}));
            methods[METHOD_contains24].setDisplayName ( "" );
            methods[METHOD_getInsets25] = new MethodDescriptor ( org.htmlparser.beans.HTMLLinkBean.class.getMethod("getInsets", new Class[] {java.awt.Insets.class}));
            methods[METHOD_getInsets25].setDisplayName ( "" );
            methods[METHOD_registerKeyboardAction26] = new MethodDescriptor ( org.htmlparser.beans.HTMLLinkBean.class.getMethod("registerKeyboardAction", new Class[] {java.awt.event.ActionListener.class, java.lang.String.class, javax.swing.KeyStroke.class, Integer.TYPE}));
            methods[METHOD_registerKeyboardAction26].setDisplayName ( "" );
            methods[METHOD_registerKeyboardAction27] = new MethodDescriptor ( org.htmlparser.beans.HTMLLinkBean.class.getMethod("registerKeyboardAction", new Class[] {java.awt.event.ActionListener.class, javax.swing.KeyStroke.class, Integer.TYPE}));
            methods[METHOD_registerKeyboardAction27].setDisplayName ( "" );
            methods[METHOD_unregisterKeyboardAction28] = new MethodDescriptor ( org.htmlparser.beans.HTMLLinkBean.class.getMethod("unregisterKeyboardAction", new Class[] {javax.swing.KeyStroke.class}));
            methods[METHOD_unregisterKeyboardAction28].setDisplayName ( "" );
            methods[METHOD_getConditionForKeyStroke29] = new MethodDescriptor ( org.htmlparser.beans.HTMLLinkBean.class.getMethod("getConditionForKeyStroke", new Class[] {javax.swing.KeyStroke.class}));
            methods[METHOD_getConditionForKeyStroke29].setDisplayName ( "" );
            methods[METHOD_getActionForKeyStroke30] = new MethodDescriptor ( org.htmlparser.beans.HTMLLinkBean.class.getMethod("getActionForKeyStroke", new Class[] {javax.swing.KeyStroke.class}));
            methods[METHOD_getActionForKeyStroke30].setDisplayName ( "" );
            methods[METHOD_resetKeyboardActions31] = new MethodDescriptor ( org.htmlparser.beans.HTMLLinkBean.class.getMethod("resetKeyboardActions", new Class[] {}));
            methods[METHOD_resetKeyboardActions31].setDisplayName ( "" );
            methods[METHOD_setInputMap32] = new MethodDescriptor ( org.htmlparser.beans.HTMLLinkBean.class.getMethod("setInputMap", new Class[] {Integer.TYPE, javax.swing.InputMap.class}));
            methods[METHOD_setInputMap32].setDisplayName ( "" );
            methods[METHOD_getInputMap33] = new MethodDescriptor ( org.htmlparser.beans.HTMLLinkBean.class.getMethod("getInputMap", new Class[] {Integer.TYPE}));
            methods[METHOD_getInputMap33].setDisplayName ( "" );
            methods[METHOD_getInputMap34] = new MethodDescriptor ( org.htmlparser.beans.HTMLLinkBean.class.getMethod("getInputMap", new Class[] {}));
            methods[METHOD_getInputMap34].setDisplayName ( "" );
            methods[METHOD_requestDefaultFocus35] = new MethodDescriptor ( org.htmlparser.beans.HTMLLinkBean.class.getMethod("requestDefaultFocus", new Class[] {}));
            methods[METHOD_requestDefaultFocus35].setDisplayName ( "" );
            methods[METHOD_getDefaultLocale36] = new MethodDescriptor ( org.htmlparser.beans.HTMLLinkBean.class.getMethod("getDefaultLocale", new Class[] {}));
            methods[METHOD_getDefaultLocale36].setDisplayName ( "" );
            methods[METHOD_setDefaultLocale37] = new MethodDescriptor ( org.htmlparser.beans.HTMLLinkBean.class.getMethod("setDefaultLocale", new Class[] {java.util.Locale.class}));
            methods[METHOD_setDefaultLocale37].setDisplayName ( "" );
            methods[METHOD_getToolTipLocation38] = new MethodDescriptor ( org.htmlparser.beans.HTMLLinkBean.class.getMethod("getToolTipLocation", new Class[] {java.awt.event.MouseEvent.class}));
            methods[METHOD_getToolTipLocation38].setDisplayName ( "" );
            methods[METHOD_createToolTip39] = new MethodDescriptor ( org.htmlparser.beans.HTMLLinkBean.class.getMethod("createToolTip", new Class[] {}));
            methods[METHOD_createToolTip39].setDisplayName ( "" );
            methods[METHOD_scrollRectToVisible40] = new MethodDescriptor ( org.htmlparser.beans.HTMLLinkBean.class.getMethod("scrollRectToVisible", new Class[] {java.awt.Rectangle.class}));
            methods[METHOD_scrollRectToVisible40].setDisplayName ( "" );
            methods[METHOD_enable41] = new MethodDescriptor ( org.htmlparser.beans.HTMLLinkBean.class.getMethod("enable", new Class[] {}));
            methods[METHOD_enable41].setDisplayName ( "" );
            methods[METHOD_disable42] = new MethodDescriptor ( org.htmlparser.beans.HTMLLinkBean.class.getMethod("disable", new Class[] {}));
            methods[METHOD_disable42].setDisplayName ( "" );
            methods[METHOD_getClientProperty43] = new MethodDescriptor ( org.htmlparser.beans.HTMLLinkBean.class.getMethod("getClientProperty", new Class[] {java.lang.Object.class}));
            methods[METHOD_getClientProperty43].setDisplayName ( "" );
            methods[METHOD_putClientProperty44] = new MethodDescriptor ( org.htmlparser.beans.HTMLLinkBean.class.getMethod("putClientProperty", new Class[] {java.lang.Object.class, java.lang.Object.class}));
            methods[METHOD_putClientProperty44].setDisplayName ( "" );
            methods[METHOD_isLightweightComponent45] = new MethodDescriptor ( org.htmlparser.beans.HTMLLinkBean.class.getMethod("isLightweightComponent", new Class[] {java.awt.Component.class}));
            methods[METHOD_isLightweightComponent45].setDisplayName ( "" );
            methods[METHOD_reshape46] = new MethodDescriptor ( org.htmlparser.beans.HTMLLinkBean.class.getMethod("reshape", new Class[] {Integer.TYPE, Integer.TYPE, Integer.TYPE, Integer.TYPE}));
            methods[METHOD_reshape46].setDisplayName ( "" );
            methods[METHOD_getBounds47] = new MethodDescriptor ( org.htmlparser.beans.HTMLLinkBean.class.getMethod("getBounds", new Class[] {java.awt.Rectangle.class}));
            methods[METHOD_getBounds47].setDisplayName ( "" );
            methods[METHOD_getSize48] = new MethodDescriptor ( org.htmlparser.beans.HTMLLinkBean.class.getMethod("getSize", new Class[] {java.awt.Dimension.class}));
            methods[METHOD_getSize48].setDisplayName ( "" );
            methods[METHOD_getLocation49] = new MethodDescriptor ( org.htmlparser.beans.HTMLLinkBean.class.getMethod("getLocation", new Class[] {java.awt.Point.class}));
            methods[METHOD_getLocation49].setDisplayName ( "" );
            methods[METHOD_computeVisibleRect50] = new MethodDescriptor ( org.htmlparser.beans.HTMLLinkBean.class.getMethod("computeVisibleRect", new Class[] {java.awt.Rectangle.class}));
            methods[METHOD_computeVisibleRect50].setDisplayName ( "" );
            methods[METHOD_firePropertyChange51] = new MethodDescriptor ( org.htmlparser.beans.HTMLLinkBean.class.getMethod("firePropertyChange", new Class[] {java.lang.String.class, Byte.TYPE, Byte.TYPE}));
            methods[METHOD_firePropertyChange51].setDisplayName ( "" );
            methods[METHOD_firePropertyChange52] = new MethodDescriptor ( org.htmlparser.beans.HTMLLinkBean.class.getMethod("firePropertyChange", new Class[] {java.lang.String.class, Character.TYPE, Character.TYPE}));
            methods[METHOD_firePropertyChange52].setDisplayName ( "" );
            methods[METHOD_firePropertyChange53] = new MethodDescriptor ( org.htmlparser.beans.HTMLLinkBean.class.getMethod("firePropertyChange", new Class[] {java.lang.String.class, Short.TYPE, Short.TYPE}));
            methods[METHOD_firePropertyChange53].setDisplayName ( "" );
            methods[METHOD_firePropertyChange54] = new MethodDescriptor ( org.htmlparser.beans.HTMLLinkBean.class.getMethod("firePropertyChange", new Class[] {java.lang.String.class, Integer.TYPE, Integer.TYPE}));
            methods[METHOD_firePropertyChange54].setDisplayName ( "" );
            methods[METHOD_firePropertyChange55] = new MethodDescriptor ( org.htmlparser.beans.HTMLLinkBean.class.getMethod("firePropertyChange", new Class[] {java.lang.String.class, Long.TYPE, Long.TYPE}));
            methods[METHOD_firePropertyChange55].setDisplayName ( "" );
            methods[METHOD_firePropertyChange56] = new MethodDescriptor ( org.htmlparser.beans.HTMLLinkBean.class.getMethod("firePropertyChange", new Class[] {java.lang.String.class, Float.TYPE, Float.TYPE}));
            methods[METHOD_firePropertyChange56].setDisplayName ( "" );
            methods[METHOD_firePropertyChange57] = new MethodDescriptor ( org.htmlparser.beans.HTMLLinkBean.class.getMethod("firePropertyChange", new Class[] {java.lang.String.class, Double.TYPE, Double.TYPE}));
            methods[METHOD_firePropertyChange57].setDisplayName ( "" );
            methods[METHOD_firePropertyChange58] = new MethodDescriptor ( org.htmlparser.beans.HTMLLinkBean.class.getMethod("firePropertyChange", new Class[] {java.lang.String.class, Boolean.TYPE, Boolean.TYPE}));
            methods[METHOD_firePropertyChange58].setDisplayName ( "" );
            methods[METHOD_addPropertyChangeListener59] = new MethodDescriptor ( org.htmlparser.beans.HTMLLinkBean.class.getMethod("addPropertyChangeListener", new Class[] {java.lang.String.class, java.beans.PropertyChangeListener.class}));
            methods[METHOD_addPropertyChangeListener59].setDisplayName ( "" );
            methods[METHOD_removePropertyChangeListener60] = new MethodDescriptor ( org.htmlparser.beans.HTMLLinkBean.class.getMethod("removePropertyChangeListener", new Class[] {java.lang.String.class, java.beans.PropertyChangeListener.class}));
            methods[METHOD_removePropertyChangeListener60].setDisplayName ( "" );
            methods[METHOD_getPropertyChangeListeners61] = new MethodDescriptor ( org.htmlparser.beans.HTMLLinkBean.class.getMethod("getPropertyChangeListeners", new Class[] {java.lang.String.class}));
            methods[METHOD_getPropertyChangeListeners61].setDisplayName ( "" );
            methods[METHOD_getListeners62] = new MethodDescriptor ( org.htmlparser.beans.HTMLLinkBean.class.getMethod("getListeners", new Class[] {java.lang.Class.class}));
            methods[METHOD_getListeners62].setDisplayName ( "" );
            methods[METHOD_addNotify63] = new MethodDescriptor ( org.htmlparser.beans.HTMLLinkBean.class.getMethod("addNotify", new Class[] {}));
            methods[METHOD_addNotify63].setDisplayName ( "" );
            methods[METHOD_removeNotify64] = new MethodDescriptor ( org.htmlparser.beans.HTMLLinkBean.class.getMethod("removeNotify", new Class[] {}));
            methods[METHOD_removeNotify64].setDisplayName ( "" );
            methods[METHOD_repaint65] = new MethodDescriptor ( org.htmlparser.beans.HTMLLinkBean.class.getMethod("repaint", new Class[] {Long.TYPE, Integer.TYPE, Integer.TYPE, Integer.TYPE, Integer.TYPE}));
            methods[METHOD_repaint65].setDisplayName ( "" );
            methods[METHOD_repaint66] = new MethodDescriptor ( org.htmlparser.beans.HTMLLinkBean.class.getMethod("repaint", new Class[] {java.awt.Rectangle.class}));
            methods[METHOD_repaint66].setDisplayName ( "" );
            methods[METHOD_revalidate67] = new MethodDescriptor ( org.htmlparser.beans.HTMLLinkBean.class.getMethod("revalidate", new Class[] {}));
            methods[METHOD_revalidate67].setDisplayName ( "" );
            methods[METHOD_paintImmediately68] = new MethodDescriptor ( org.htmlparser.beans.HTMLLinkBean.class.getMethod("paintImmediately", new Class[] {Integer.TYPE, Integer.TYPE, Integer.TYPE, Integer.TYPE}));
            methods[METHOD_paintImmediately68].setDisplayName ( "" );
            methods[METHOD_paintImmediately69] = new MethodDescriptor ( org.htmlparser.beans.HTMLLinkBean.class.getMethod("paintImmediately", new Class[] {java.awt.Rectangle.class}));
            methods[METHOD_paintImmediately69].setDisplayName ( "" );
            methods[METHOD_countComponents70] = new MethodDescriptor ( org.htmlparser.beans.HTMLLinkBean.class.getMethod("countComponents", new Class[] {}));
            methods[METHOD_countComponents70].setDisplayName ( "" );
            methods[METHOD_insets71] = new MethodDescriptor ( org.htmlparser.beans.HTMLLinkBean.class.getMethod("insets", new Class[] {}));
            methods[METHOD_insets71].setDisplayName ( "" );
            methods[METHOD_add72] = new MethodDescriptor ( org.htmlparser.beans.HTMLLinkBean.class.getMethod("add", new Class[] {java.awt.Component.class}));
            methods[METHOD_add72].setDisplayName ( "" );
            methods[METHOD_add73] = new MethodDescriptor ( org.htmlparser.beans.HTMLLinkBean.class.getMethod("add", new Class[] {java.lang.String.class, java.awt.Component.class}));
            methods[METHOD_add73].setDisplayName ( "" );
            methods[METHOD_add74] = new MethodDescriptor ( org.htmlparser.beans.HTMLLinkBean.class.getMethod("add", new Class[] {java.awt.Component.class, Integer.TYPE}));
            methods[METHOD_add74].setDisplayName ( "" );
            methods[METHOD_add75] = new MethodDescriptor ( org.htmlparser.beans.HTMLLinkBean.class.getMethod("add", new Class[] {java.awt.Component.class, java.lang.Object.class}));
            methods[METHOD_add75].setDisplayName ( "" );
            methods[METHOD_add76] = new MethodDescriptor ( org.htmlparser.beans.HTMLLinkBean.class.getMethod("add", new Class[] {java.awt.Component.class, java.lang.Object.class, Integer.TYPE}));
            methods[METHOD_add76].setDisplayName ( "" );
            methods[METHOD_remove77] = new MethodDescriptor ( org.htmlparser.beans.HTMLLinkBean.class.getMethod("remove", new Class[] {Integer.TYPE}));
            methods[METHOD_remove77].setDisplayName ( "" );
            methods[METHOD_remove78] = new MethodDescriptor ( org.htmlparser.beans.HTMLLinkBean.class.getMethod("remove", new Class[] {java.awt.Component.class}));
            methods[METHOD_remove78].setDisplayName ( "" );
            methods[METHOD_removeAll79] = new MethodDescriptor ( org.htmlparser.beans.HTMLLinkBean.class.getMethod("removeAll", new Class[] {}));
            methods[METHOD_removeAll79].setDisplayName ( "" );
            methods[METHOD_doLayout80] = new MethodDescriptor ( org.htmlparser.beans.HTMLLinkBean.class.getMethod("doLayout", new Class[] {}));
            methods[METHOD_doLayout80].setDisplayName ( "" );
            methods[METHOD_layout81] = new MethodDescriptor ( org.htmlparser.beans.HTMLLinkBean.class.getMethod("layout", new Class[] {}));
            methods[METHOD_layout81].setDisplayName ( "" );
            methods[METHOD_invalidate82] = new MethodDescriptor ( org.htmlparser.beans.HTMLLinkBean.class.getMethod("invalidate", new Class[] {}));
            methods[METHOD_invalidate82].setDisplayName ( "" );
            methods[METHOD_validate83] = new MethodDescriptor ( org.htmlparser.beans.HTMLLinkBean.class.getMethod("validate", new Class[] {}));
            methods[METHOD_validate83].setDisplayName ( "" );
            methods[METHOD_preferredSize84] = new MethodDescriptor ( org.htmlparser.beans.HTMLLinkBean.class.getMethod("preferredSize", new Class[] {}));
            methods[METHOD_preferredSize84].setDisplayName ( "" );
            methods[METHOD_minimumSize85] = new MethodDescriptor ( org.htmlparser.beans.HTMLLinkBean.class.getMethod("minimumSize", new Class[] {}));
            methods[METHOD_minimumSize85].setDisplayName ( "" );
            methods[METHOD_paintComponents86] = new MethodDescriptor ( org.htmlparser.beans.HTMLLinkBean.class.getMethod("paintComponents", new Class[] {java.awt.Graphics.class}));
            methods[METHOD_paintComponents86].setDisplayName ( "" );
            methods[METHOD_printComponents87] = new MethodDescriptor ( org.htmlparser.beans.HTMLLinkBean.class.getMethod("printComponents", new Class[] {java.awt.Graphics.class}));
            methods[METHOD_printComponents87].setDisplayName ( "" );
            methods[METHOD_deliverEvent88] = new MethodDescriptor ( org.htmlparser.beans.HTMLLinkBean.class.getMethod("deliverEvent", new Class[] {java.awt.Event.class}));
            methods[METHOD_deliverEvent88].setDisplayName ( "" );
            methods[METHOD_getComponentAt89] = new MethodDescriptor ( org.htmlparser.beans.HTMLLinkBean.class.getMethod("getComponentAt", new Class[] {Integer.TYPE, Integer.TYPE}));
            methods[METHOD_getComponentAt89].setDisplayName ( "" );
            methods[METHOD_locate90] = new MethodDescriptor ( org.htmlparser.beans.HTMLLinkBean.class.getMethod("locate", new Class[] {Integer.TYPE, Integer.TYPE}));
            methods[METHOD_locate90].setDisplayName ( "" );
            methods[METHOD_getComponentAt91] = new MethodDescriptor ( org.htmlparser.beans.HTMLLinkBean.class.getMethod("getComponentAt", new Class[] {java.awt.Point.class}));
            methods[METHOD_getComponentAt91].setDisplayName ( "" );
            methods[METHOD_findComponentAt92] = new MethodDescriptor ( org.htmlparser.beans.HTMLLinkBean.class.getMethod("findComponentAt", new Class[] {Integer.TYPE, Integer.TYPE}));
            methods[METHOD_findComponentAt92].setDisplayName ( "" );
            methods[METHOD_findComponentAt93] = new MethodDescriptor ( org.htmlparser.beans.HTMLLinkBean.class.getMethod("findComponentAt", new Class[] {java.awt.Point.class}));
            methods[METHOD_findComponentAt93].setDisplayName ( "" );
            methods[METHOD_isAncestorOf94] = new MethodDescriptor ( org.htmlparser.beans.HTMLLinkBean.class.getMethod("isAncestorOf", new Class[] {java.awt.Component.class}));
            methods[METHOD_isAncestorOf94].setDisplayName ( "" );
            methods[METHOD_list95] = new MethodDescriptor ( org.htmlparser.beans.HTMLLinkBean.class.getMethod("list", new Class[] {java.io.PrintStream.class, Integer.TYPE}));
            methods[METHOD_list95].setDisplayName ( "" );
            methods[METHOD_list96] = new MethodDescriptor ( org.htmlparser.beans.HTMLLinkBean.class.getMethod("list", new Class[] {java.io.PrintWriter.class, Integer.TYPE}));
            methods[METHOD_list96].setDisplayName ( "" );
            methods[METHOD_areFocusTraversalKeysSet97] = new MethodDescriptor ( org.htmlparser.beans.HTMLLinkBean.class.getMethod("areFocusTraversalKeysSet", new Class[] {Integer.TYPE}));
            methods[METHOD_areFocusTraversalKeysSet97].setDisplayName ( "" );
            methods[METHOD_isFocusCycleRoot98] = new MethodDescriptor ( org.htmlparser.beans.HTMLLinkBean.class.getMethod("isFocusCycleRoot", new Class[] {java.awt.Container.class}));
            methods[METHOD_isFocusCycleRoot98].setDisplayName ( "" );
            methods[METHOD_transferFocusBackward99] = new MethodDescriptor ( org.htmlparser.beans.HTMLLinkBean.class.getMethod("transferFocusBackward", new Class[] {}));
            methods[METHOD_transferFocusBackward99].setDisplayName ( "" );
            methods[METHOD_transferFocusDownCycle100] = new MethodDescriptor ( org.htmlparser.beans.HTMLLinkBean.class.getMethod("transferFocusDownCycle", new Class[] {}));
            methods[METHOD_transferFocusDownCycle100].setDisplayName ( "" );
            methods[METHOD_applyComponentOrientation101] = new MethodDescriptor ( org.htmlparser.beans.HTMLLinkBean.class.getMethod("applyComponentOrientation", new Class[] {java.awt.ComponentOrientation.class}));
            methods[METHOD_applyComponentOrientation101].setDisplayName ( "" );
            methods[METHOD_enable102] = new MethodDescriptor ( org.htmlparser.beans.HTMLLinkBean.class.getMethod("enable", new Class[] {Boolean.TYPE}));
            methods[METHOD_enable102].setDisplayName ( "" );
            methods[METHOD_enableInputMethods103] = new MethodDescriptor ( org.htmlparser.beans.HTMLLinkBean.class.getMethod("enableInputMethods", new Class[] {Boolean.TYPE}));
            methods[METHOD_enableInputMethods103].setDisplayName ( "" );
            methods[METHOD_show104] = new MethodDescriptor ( org.htmlparser.beans.HTMLLinkBean.class.getMethod("show", new Class[] {}));
            methods[METHOD_show104].setDisplayName ( "" );
            methods[METHOD_show105] = new MethodDescriptor ( org.htmlparser.beans.HTMLLinkBean.class.getMethod("show", new Class[] {Boolean.TYPE}));
            methods[METHOD_show105].setDisplayName ( "" );
            methods[METHOD_hide106] = new MethodDescriptor ( org.htmlparser.beans.HTMLLinkBean.class.getMethod("hide", new Class[] {}));
            methods[METHOD_hide106].setDisplayName ( "" );
            methods[METHOD_getLocation107] = new MethodDescriptor ( org.htmlparser.beans.HTMLLinkBean.class.getMethod("getLocation", new Class[] {}));
            methods[METHOD_getLocation107].setDisplayName ( "" );
            methods[METHOD_location108] = new MethodDescriptor ( org.htmlparser.beans.HTMLLinkBean.class.getMethod("location", new Class[] {}));
            methods[METHOD_location108].setDisplayName ( "" );
            methods[METHOD_setLocation109] = new MethodDescriptor ( org.htmlparser.beans.HTMLLinkBean.class.getMethod("setLocation", new Class[] {Integer.TYPE, Integer.TYPE}));
            methods[METHOD_setLocation109].setDisplayName ( "" );
            methods[METHOD_move110] = new MethodDescriptor ( org.htmlparser.beans.HTMLLinkBean.class.getMethod("move", new Class[] {Integer.TYPE, Integer.TYPE}));
            methods[METHOD_move110].setDisplayName ( "" );
            methods[METHOD_setLocation111] = new MethodDescriptor ( org.htmlparser.beans.HTMLLinkBean.class.getMethod("setLocation", new Class[] {java.awt.Point.class}));
            methods[METHOD_setLocation111].setDisplayName ( "" );
            methods[METHOD_getSize112] = new MethodDescriptor ( org.htmlparser.beans.HTMLLinkBean.class.getMethod("getSize", new Class[] {}));
            methods[METHOD_getSize112].setDisplayName ( "" );
            methods[METHOD_size113] = new MethodDescriptor ( org.htmlparser.beans.HTMLLinkBean.class.getMethod("size", new Class[] {}));
            methods[METHOD_size113].setDisplayName ( "" );
            methods[METHOD_setSize114] = new MethodDescriptor ( org.htmlparser.beans.HTMLLinkBean.class.getMethod("setSize", new Class[] {Integer.TYPE, Integer.TYPE}));
            methods[METHOD_setSize114].setDisplayName ( "" );
            methods[METHOD_resize115] = new MethodDescriptor ( org.htmlparser.beans.HTMLLinkBean.class.getMethod("resize", new Class[] {Integer.TYPE, Integer.TYPE}));
            methods[METHOD_resize115].setDisplayName ( "" );
            methods[METHOD_setSize116] = new MethodDescriptor ( org.htmlparser.beans.HTMLLinkBean.class.getMethod("setSize", new Class[] {java.awt.Dimension.class}));
            methods[METHOD_setSize116].setDisplayName ( "" );
            methods[METHOD_resize117] = new MethodDescriptor ( org.htmlparser.beans.HTMLLinkBean.class.getMethod("resize", new Class[] {java.awt.Dimension.class}));
            methods[METHOD_resize117].setDisplayName ( "" );
            methods[METHOD_bounds118] = new MethodDescriptor ( org.htmlparser.beans.HTMLLinkBean.class.getMethod("bounds", new Class[] {}));
            methods[METHOD_bounds118].setDisplayName ( "" );
            methods[METHOD_setBounds119] = new MethodDescriptor ( org.htmlparser.beans.HTMLLinkBean.class.getMethod("setBounds", new Class[] {Integer.TYPE, Integer.TYPE, Integer.TYPE, Integer.TYPE}));
            methods[METHOD_setBounds119].setDisplayName ( "" );
            methods[METHOD_getFontMetrics120] = new MethodDescriptor ( org.htmlparser.beans.HTMLLinkBean.class.getMethod("getFontMetrics", new Class[] {java.awt.Font.class}));
            methods[METHOD_getFontMetrics120].setDisplayName ( "" );
            methods[METHOD_paintAll121] = new MethodDescriptor ( org.htmlparser.beans.HTMLLinkBean.class.getMethod("paintAll", new Class[] {java.awt.Graphics.class}));
            methods[METHOD_paintAll121].setDisplayName ( "" );
            methods[METHOD_repaint122] = new MethodDescriptor ( org.htmlparser.beans.HTMLLinkBean.class.getMethod("repaint", new Class[] {}));
            methods[METHOD_repaint122].setDisplayName ( "" );
            methods[METHOD_repaint123] = new MethodDescriptor ( org.htmlparser.beans.HTMLLinkBean.class.getMethod("repaint", new Class[] {Long.TYPE}));
            methods[METHOD_repaint123].setDisplayName ( "" );
            methods[METHOD_repaint124] = new MethodDescriptor ( org.htmlparser.beans.HTMLLinkBean.class.getMethod("repaint", new Class[] {Integer.TYPE, Integer.TYPE, Integer.TYPE, Integer.TYPE}));
            methods[METHOD_repaint124].setDisplayName ( "" );
            methods[METHOD_imageUpdate125] = new MethodDescriptor ( org.htmlparser.beans.HTMLLinkBean.class.getMethod("imageUpdate", new Class[] {java.awt.Image.class, Integer.TYPE, Integer.TYPE, Integer.TYPE, Integer.TYPE, Integer.TYPE}));
            methods[METHOD_imageUpdate125].setDisplayName ( "" );
            methods[METHOD_createImage126] = new MethodDescriptor ( org.htmlparser.beans.HTMLLinkBean.class.getMethod("createImage", new Class[] {java.awt.image.ImageProducer.class}));
            methods[METHOD_createImage126].setDisplayName ( "" );
            methods[METHOD_createImage127] = new MethodDescriptor ( org.htmlparser.beans.HTMLLinkBean.class.getMethod("createImage", new Class[] {Integer.TYPE, Integer.TYPE}));
            methods[METHOD_createImage127].setDisplayName ( "" );
            methods[METHOD_createVolatileImage128] = new MethodDescriptor ( org.htmlparser.beans.HTMLLinkBean.class.getMethod("createVolatileImage", new Class[] {Integer.TYPE, Integer.TYPE}));
            methods[METHOD_createVolatileImage128].setDisplayName ( "" );
            methods[METHOD_createVolatileImage129] = new MethodDescriptor ( org.htmlparser.beans.HTMLLinkBean.class.getMethod("createVolatileImage", new Class[] {Integer.TYPE, Integer.TYPE, java.awt.ImageCapabilities.class}));
            methods[METHOD_createVolatileImage129].setDisplayName ( "" );
            methods[METHOD_prepareImage130] = new MethodDescriptor ( org.htmlparser.beans.HTMLLinkBean.class.getMethod("prepareImage", new Class[] {java.awt.Image.class, java.awt.image.ImageObserver.class}));
            methods[METHOD_prepareImage130].setDisplayName ( "" );
            methods[METHOD_prepareImage131] = new MethodDescriptor ( org.htmlparser.beans.HTMLLinkBean.class.getMethod("prepareImage", new Class[] {java.awt.Image.class, Integer.TYPE, Integer.TYPE, java.awt.image.ImageObserver.class}));
            methods[METHOD_prepareImage131].setDisplayName ( "" );
            methods[METHOD_checkImage132] = new MethodDescriptor ( org.htmlparser.beans.HTMLLinkBean.class.getMethod("checkImage", new Class[] {java.awt.Image.class, java.awt.image.ImageObserver.class}));
            methods[METHOD_checkImage132].setDisplayName ( "" );
            methods[METHOD_checkImage133] = new MethodDescriptor ( org.htmlparser.beans.HTMLLinkBean.class.getMethod("checkImage", new Class[] {java.awt.Image.class, Integer.TYPE, Integer.TYPE, java.awt.image.ImageObserver.class}));
            methods[METHOD_checkImage133].setDisplayName ( "" );
            methods[METHOD_inside134] = new MethodDescriptor ( org.htmlparser.beans.HTMLLinkBean.class.getMethod("inside", new Class[] {Integer.TYPE, Integer.TYPE}));
            methods[METHOD_inside134].setDisplayName ( "" );
            methods[METHOD_contains135] = new MethodDescriptor ( org.htmlparser.beans.HTMLLinkBean.class.getMethod("contains", new Class[] {java.awt.Point.class}));
            methods[METHOD_contains135].setDisplayName ( "" );
            methods[METHOD_dispatchEvent136] = new MethodDescriptor ( org.htmlparser.beans.HTMLLinkBean.class.getMethod("dispatchEvent", new Class[] {java.awt.AWTEvent.class}));
            methods[METHOD_dispatchEvent136].setDisplayName ( "" );
            methods[METHOD_postEvent137] = new MethodDescriptor ( org.htmlparser.beans.HTMLLinkBean.class.getMethod("postEvent", new Class[] {java.awt.Event.class}));
            methods[METHOD_postEvent137].setDisplayName ( "" );
            methods[METHOD_handleEvent138] = new MethodDescriptor ( org.htmlparser.beans.HTMLLinkBean.class.getMethod("handleEvent", new Class[] {java.awt.Event.class}));
            methods[METHOD_handleEvent138].setDisplayName ( "" );
            methods[METHOD_mouseDown139] = new MethodDescriptor ( org.htmlparser.beans.HTMLLinkBean.class.getMethod("mouseDown", new Class[] {java.awt.Event.class, Integer.TYPE, Integer.TYPE}));
            methods[METHOD_mouseDown139].setDisplayName ( "" );
            methods[METHOD_mouseDrag140] = new MethodDescriptor ( org.htmlparser.beans.HTMLLinkBean.class.getMethod("mouseDrag", new Class[] {java.awt.Event.class, Integer.TYPE, Integer.TYPE}));
            methods[METHOD_mouseDrag140].setDisplayName ( "" );
            methods[METHOD_mouseUp141] = new MethodDescriptor ( org.htmlparser.beans.HTMLLinkBean.class.getMethod("mouseUp", new Class[] {java.awt.Event.class, Integer.TYPE, Integer.TYPE}));
            methods[METHOD_mouseUp141].setDisplayName ( "" );
            methods[METHOD_mouseMove142] = new MethodDescriptor ( org.htmlparser.beans.HTMLLinkBean.class.getMethod("mouseMove", new Class[] {java.awt.Event.class, Integer.TYPE, Integer.TYPE}));
            methods[METHOD_mouseMove142].setDisplayName ( "" );
            methods[METHOD_mouseEnter143] = new MethodDescriptor ( org.htmlparser.beans.HTMLLinkBean.class.getMethod("mouseEnter", new Class[] {java.awt.Event.class, Integer.TYPE, Integer.TYPE}));
            methods[METHOD_mouseEnter143].setDisplayName ( "" );
            methods[METHOD_mouseExit144] = new MethodDescriptor ( org.htmlparser.beans.HTMLLinkBean.class.getMethod("mouseExit", new Class[] {java.awt.Event.class, Integer.TYPE, Integer.TYPE}));
            methods[METHOD_mouseExit144].setDisplayName ( "" );
            methods[METHOD_keyDown145] = new MethodDescriptor ( org.htmlparser.beans.HTMLLinkBean.class.getMethod("keyDown", new Class[] {java.awt.Event.class, Integer.TYPE}));
            methods[METHOD_keyDown145].setDisplayName ( "" );
            methods[METHOD_keyUp146] = new MethodDescriptor ( org.htmlparser.beans.HTMLLinkBean.class.getMethod("keyUp", new Class[] {java.awt.Event.class, Integer.TYPE}));
            methods[METHOD_keyUp146].setDisplayName ( "" );
            methods[METHOD_action147] = new MethodDescriptor ( org.htmlparser.beans.HTMLLinkBean.class.getMethod("action", new Class[] {java.awt.Event.class, java.lang.Object.class}));
            methods[METHOD_action147].setDisplayName ( "" );
            methods[METHOD_gotFocus148] = new MethodDescriptor ( org.htmlparser.beans.HTMLLinkBean.class.getMethod("gotFocus", new Class[] {java.awt.Event.class, java.lang.Object.class}));
            methods[METHOD_gotFocus148].setDisplayName ( "" );
            methods[METHOD_lostFocus149] = new MethodDescriptor ( org.htmlparser.beans.HTMLLinkBean.class.getMethod("lostFocus", new Class[] {java.awt.Event.class, java.lang.Object.class}));
            methods[METHOD_lostFocus149].setDisplayName ( "" );
            methods[METHOD_transferFocus150] = new MethodDescriptor ( org.htmlparser.beans.HTMLLinkBean.class.getMethod("transferFocus", new Class[] {}));
            methods[METHOD_transferFocus150].setDisplayName ( "" );
            methods[METHOD_nextFocus151] = new MethodDescriptor ( org.htmlparser.beans.HTMLLinkBean.class.getMethod("nextFocus", new Class[] {}));
            methods[METHOD_nextFocus151].setDisplayName ( "" );
            methods[METHOD_transferFocusUpCycle152] = new MethodDescriptor ( org.htmlparser.beans.HTMLLinkBean.class.getMethod("transferFocusUpCycle", new Class[] {}));
            methods[METHOD_transferFocusUpCycle152].setDisplayName ( "" );
            methods[METHOD_hasFocus153] = new MethodDescriptor ( org.htmlparser.beans.HTMLLinkBean.class.getMethod("hasFocus", new Class[] {}));
            methods[METHOD_hasFocus153].setDisplayName ( "" );
            methods[METHOD_add154] = new MethodDescriptor ( org.htmlparser.beans.HTMLLinkBean.class.getMethod("add", new Class[] {java.awt.PopupMenu.class}));
            methods[METHOD_add154].setDisplayName ( "" );
            methods[METHOD_remove155] = new MethodDescriptor ( org.htmlparser.beans.HTMLLinkBean.class.getMethod("remove", new Class[] {java.awt.MenuComponent.class}));
            methods[METHOD_remove155].setDisplayName ( "" );
            methods[METHOD_toString156] = new MethodDescriptor ( org.htmlparser.beans.HTMLLinkBean.class.getMethod("toString", new Class[] {}));
            methods[METHOD_toString156].setDisplayName ( "" );
            methods[METHOD_list157] = new MethodDescriptor ( org.htmlparser.beans.HTMLLinkBean.class.getMethod("list", new Class[] {}));
            methods[METHOD_list157].setDisplayName ( "" );
            methods[METHOD_list158] = new MethodDescriptor ( org.htmlparser.beans.HTMLLinkBean.class.getMethod("list", new Class[] {java.io.PrintStream.class}));
            methods[METHOD_list158].setDisplayName ( "" );
            methods[METHOD_list159] = new MethodDescriptor ( org.htmlparser.beans.HTMLLinkBean.class.getMethod("list", new Class[] {java.io.PrintWriter.class}));
            methods[METHOD_list159].setDisplayName ( "" );
        }
        catch( Exception e) {}//GEN-HEADEREND:Methods
        
        // Here you can add code for customizing the methods array.
        
        return methods;         }//GEN-LAST:Methods
    
    private static java.awt.Image iconColor16 = null; //GEN-BEGIN:IconsDef
    private static java.awt.Image iconColor32 = null;
    private static java.awt.Image iconMono16 = null;
    private static java.awt.Image iconMono32 = null; //GEN-END:IconsDef
    private static String iconNameC16 = "images/Chain16.gif";//GEN-BEGIN:Icons
    private static String iconNameC32 = "images/Chain32.gif";
    private static String iconNameM16 = "images/Chain16.gif";
    private static String iconNameM32 = "images/Chain32.gif";//GEN-END:Icons
    
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

