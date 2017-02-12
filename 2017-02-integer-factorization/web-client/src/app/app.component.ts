import {Component} from "@angular/core";
import {Http} from "@angular/http";
import {FactorizedNumber} from "./domain/FactorizedNumber";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.less']
})
export class AppComponent {
  private numberToFactorize = 1;
  private factorizedNumbers: FactorizedNumber[] = [];

  constructor(private http: Http) {
  }

  factorize(numberToFactorize: number): void {
    this.http.get(`/api/prime-factorize/${numberToFactorize}`)
      .map(response => {
        console.log(response);
        return response;
      })
      .map(response => response.json() as number[])
      .subscribe((factors: number[]) => {
        this.factorizedNumbers.push({
          numberToFactorize: numberToFactorize,
          factors: factors
        });
      });
  }
}
