import { Component, OnDestroy, OnInit } from '@angular/core';
import { City } from 'src/app/core/models/city';
import { Params } from 'src/app/core/models/params';
import { CityService } from 'src/app/core/services/city.service';

import { takeWhile } from 'rxjs/operators';

import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent implements OnInit, OnDestroy {

  cities: City[] = [];
  totalElements = 0;
  params: Params = {
    page: 0,
    size: 5
  };
  alive = true;

  constructor(private cityService: CityService, private toast: ToastrService) { }

  ngOnInit(): void {
    this.loadCities();
  }

  loadCities(pageNumber: number = 0, search?: string) {
    this.params.page = pageNumber;
    this.params.search = search || '';

    this.cityService
      .findAll(this.params)
      .pipe(takeWhile(() => this.alive))
      .subscribe(pageCity => {

        if (this.params.page === 0) {
          this.cities = pageCity.content;
        } else {
          this.cities = this.cities.concat(pageCity.content);
        }

        this.totalElements = pageCity.totalElements;
        },
        error => this.onError('Ocorreu um erro ao buscar as cidades. Tente novamente mais tarde.')
      );
  }

  public onSearch(search: string): void {
    this.loadCities(0, search);
  }

  public onAdd(name): void {
    this.cityService
      .save(name)
      .pipe(takeWhile(() => this.alive))
      .subscribe(
        city => {
          this.cities.push(city);
          this.onSuccess('Cidade cadastrada com sucesso');
        },
        (error) => this.onError('Verifique se a cidade já foi cadastrada ou tente novamente mais tarde.')
      );
  }

  public onNextPage() {
    this.loadCities(this.params.page + 1, this.params.search);
  }

  ngOnDestroy(): void {
    this.alive = false;
  }

  private onError(text: string): void {
    this.toast.error(text);
  }

  private onSuccess(text: string): void {
    this.toast.success(text);
  }

}
