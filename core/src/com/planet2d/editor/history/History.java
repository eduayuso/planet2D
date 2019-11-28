package com.planet2d.editor.history;

import com.badlogic.gdx.utils.Array;

public class History {
	
	private final static int MAX_LIST_SIZE = 10;
	
	private Array<Snapshot> snapshotList;
	private int currentPos;
	
	public History() {
		
		this.snapshotList = new Array<Snapshot>();
		this.snapshotList.ordered = true;
		this.currentPos = 0;
	}
	
	public void saveSnapshot() {
		
		// Insert by the 'currentpos' index, this must be the last, so the next item in the list are removed
		if (this.currentPos < this.snapshotList.size-1) {
			this.snapshotList.removeRange(this.currentPos+1, this.snapshotList.size-1);
		}
		this.snapshotList.add(new Snapshot());
		
		// Check size. If greater, we remove the half of list, to avoid to resize many times
		if (this.snapshotList.size > MAX_LIST_SIZE) {
			this.snapshotList.removeRange(0, MAX_LIST_SIZE/2);
		}
		
		// Current pos points to the last snapshot
		this.currentPos = this.snapshotList.size-1;
	}
	
	public void undo() {
		
		if (this.currentPos > 0) {
			this.currentPos--;
			this.restoreSnapshot();
		}
	}
	
	public void redo() {
		
		if (this.currentPos < snapshotList.size-1) {
			this.currentPos++;
			this.restoreSnapshot();
		}
	}
	
	private void restoreSnapshot() {

		Snapshot snapshot = snapshotList.get(currentPos);
		snapshot.restore();
	}
}
