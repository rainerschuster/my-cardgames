package com.rainerschuster.cardgames.client.dnd;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.allen_sauer.gwt.dnd.client.DragHandler;
import com.allen_sauer.gwt.dnd.client.PickupDragController;
import com.allen_sauer.gwt.dnd.client.drop.DropController;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author Rainer Schuster
 */
public final class DNDManager {

	private static final Logger logger = Logger.getLogger(DNDManager.class.getName());

	private static List<DropController> dropControllers = new ArrayList<DropController>();
	private static List<DragHandler> dragHandlers = new ArrayList<DragHandler>();

	private static PickupDragController dragController = null;

//	public static PickupDragController getDragController() {
//		return dragController;
//	}

	public static void setDragController(PickupDragController dragController) {
		DNDManager.dragController = dragController;
	}

	public static void unregisterAll() {
		logger.log(Level.INFO, "unregisterAll");
		for (DragHandler dragHandler : dragHandlers) {
			dragController.removeDragHandler(dragHandler);
		}
		dragHandlers.clear();
		unregisterDropControllers();
	}

	public static void registerDropController(final DropController dropController) {
		logger.log(Level.INFO, "registerDropController");
		dragController.registerDropController(dropController);
		dropControllers.add(dropController);
	}

	public static void unregisterDropController(final DropController dropController) {
		logger.log(Level.INFO, "unregisterDropController");
		dragController.unregisterDropController(dropController);
		dropControllers.remove(dropController);
	}

	public static void unregisterDropControllers() {
		logger.log(Level.INFO, "unregisterDropControllers");
		dragController.unregisterDropControllers();
		dropControllers.clear();
	}

	public static void addDragHandler(final DragHandler handler) {
		logger.log(Level.INFO, "addDragHandler");
		dragController.addDragHandler(handler);
		dragHandlers.add(handler);
	}
	public static void removeDragHandler(final DragHandler handler) {
		logger.log(Level.INFO, "removeDragHandler");
		dragController.removeDragHandler(handler);
		dragHandlers.remove(handler);
	}
	public static void makeDraggable(final Widget draggable) {
		logger.log(Level.INFO, "makeDraggable");
		dragController.makeDraggable(draggable);
	}
	public static void makeDraggable(final Widget draggable, final Widget dragHandle) {
		logger.log(Level.INFO, "makeDraggable");
		dragController.makeDraggable(draggable, dragHandle);
	}
	public static void makeNotDraggable(final Widget draggable) {
		logger.log(Level.INFO, "makeNotDraggable");
		dragController.makeNotDraggable(draggable);
	}

	public static boolean isDropTarget(final Widget widget) {
		logger.log(Level.INFO, "isDropTarget");
		for (DropController dropController : dropControllers) {
			if (dropController.getDropTarget() == widget) {
				return true;
			}
		}
		return false;
	}

	public static DropController getDropTarget(final Widget widget) {
		logger.log(Level.INFO, "getDropTarget");
		for (DropController dropController : dropControllers) {
			if (dropController.getDropTarget() == widget) {
				return dropController;
			}
		}
		return null;
	}

}
