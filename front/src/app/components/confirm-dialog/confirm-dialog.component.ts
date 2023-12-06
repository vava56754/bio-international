import { Component, Input } from '@angular/core';
import { MatDialogRef } from '@angular/material/dialog';
@Component({
  selector: 'app-confirm-dialog',
  templateUrl: './confirm-dialog.component.html',
  styleUrls: ['./confirm-dialog.component.scss']
})
export class ConfirmDialogComponent {
  title: string = 'Confirmation de suppression';
  message: string = 'Voulez-vous vraiment supprimer cet élément de manière permanente ?';
  cancel:string = 'Annuler'
  action: string = 'Supprimer'
  constructor(public dialogRef: MatDialogRef<ConfirmDialogComponent>) {}

  closeDialog(confirm: boolean): void {
    this.dialogRef.close(confirm);
  }
}
