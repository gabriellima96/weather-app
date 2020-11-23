import { Component, Input, OnInit } from '@angular/core';
import { City } from 'src/app/core/models/city';

@Component({
  selector: 'app-list-item',
  templateUrl: './list-item.component.html',
  styleUrls: ['./list-item.component.scss']
})
export class ListItemComponent {

  @Input()
  public city: City;

  constructor() { }

  public getLink(): string {
    return `/forecast/${this.city.id}`;
  }

}
