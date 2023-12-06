import { Component, Input } from '@angular/core';

@Component({
  selector: 'app-picture-content',
  templateUrl: './picture-content.component.html',
  styleUrls: ['./picture-content.component.scss']
})
export class PictureContentComponent {
  @Input() title: string='';
  @Input() src: string='';
  @Input() alt: string='';
}
