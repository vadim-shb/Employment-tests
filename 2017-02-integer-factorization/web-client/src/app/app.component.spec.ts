/* tslint:disable:no-unused-variable */
import {TestBed, async} from "@angular/core/testing";
import {AppComponent} from "./app.component";
import {MaterialModule} from "@angular/material";
import {FormsModule} from "@angular/forms";
import {
  Http,
  BaseRequestOptions,
  XHRBackend,
  HttpModule,
  RequestMethod,
  Response,
  ResponseOptions
} from "@angular/http";
import {MockBackend, MockConnection} from "@angular/http/testing";

describe('AppComponent', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [
        AppComponent
      ],
      imports: [
        FormsModule,
        HttpModule,
        MaterialModule.forRoot()
      ],
      providers: [
        MockBackend,
        BaseRequestOptions,
        {
          provide: Http,
          deps: [MockBackend, BaseRequestOptions],
          useFactory: (backend: XHRBackend, defaultOptions: BaseRequestOptions) => {
            return new Http(backend, defaultOptions);
          }
        }

      ]
    });
    TestBed.compileComponents();
  });

  it('should create the app', async(() => {
    const fixture = TestBed.createComponent(AppComponent);
    const app = fixture.debugElement.componentInstance;
    expect(app).toBeTruthy();
  }));

  // it(`should have as title 'app works!'`, async(() => {
  //   const fixture = TestBed.createComponent(AppComponent);
  //   const app = fixture.debugElement.componentInstance;
  //   expect(app.title).toEqual('app works!');
  // }));

  // it('should render title in a h2 tag', async(() => {
  //   const fixture = TestBed.createComponent(AppComponent);
  //   fixture.detectChanges();
  //   const compiled = fixture.debugElement.nativeElement;
  //   expect(compiled.querySelector('h2').textContent).toContain('Enter number:');
  // }));

  it('should set default numberToFactorize to 1', async(() => {
    const fixture = TestBed.createComponent(AppComponent);
    const app = fixture.debugElement.componentInstance;
    expect(app.numberToFactorize).toBe(1);
  }));

  it('should render configured "Number to factorize" input', async(() => {
    const fixture = TestBed.createComponent(AppComponent);
    const app = fixture.debugElement.componentInstance;

    fixture.detectChanges();
    fixture.whenStable().then(() => {
      const compiled = fixture.debugElement.nativeElement;
      const input = compiled.querySelector('input');
      expect(input).toBeDefined();
      expect(input.placeholder).toBe('Number to factorize');
      expect(input.type).toBe('number');
      expect(input.min).toBe('1');
      expect(input.max).toBe(Number.MAX_SAFE_INTEGER.toString());
      expect(input.value).toBe(app.numberToFactorize.toString());
    });
  }));

  it('should render configured "Factorize" button', async(() => {
    const fixture = TestBed.createComponent(AppComponent);
    fixture.detectChanges();
    fixture.whenStable().then(() => {
      const compiled = fixture.debugElement.nativeElement;
      const button = compiled.querySelector('button');
      expect(button).toBeDefined();
      expect(button.innerHTML.trim()).toContain('Factorize');
    });
  }));

  it('should call factorize(numberToFactorize) method on "Factorize" button click', async(() => {
    const fixture = TestBed.createComponent(AppComponent);
    const app = fixture.debugElement.componentInstance;
    spyOn(app, 'factorize');
    app.numberToFactorize = 10;

    fixture.detectChanges();
    fixture.whenStable().then(() => {
      const compiled = fixture.debugElement.nativeElement;
      const button = compiled.querySelector('button');
      button.click();
      expect(app.factorize).toHaveBeenCalledWith(app.numberToFactorize);
    });
  }));

  it('should send HTTP request on factorize() method and add result to result list', async(() => {
    const fixture = TestBed.createComponent(AppComponent);
    const app = fixture.debugElement.componentInstance;
    const mockBackend = TestBed.get(MockBackend);
    mockBackend.connections.subscribe((connection: MockConnection) => {
      if (connection.request.url === '/api/prime-factorize/10' && connection.request.method === RequestMethod.Get) {
        connection.mockRespond(new Response(new ResponseOptions({
          body: JSON.stringify([2, 5])
        })));
      }
    });
    app.factorizedNumbers = [];

    app.factorize(10);

    fixture.detectChanges();
    fixture.whenStable().then(() => {
      expect(app.factorizedNumbers).toContain({
        numberToFactorize: 10,
        factors: [2, 5]
      });
    });

  }));

  it('should render factorized results', async(() => {
    const fixture = TestBed.createComponent(AppComponent);
    const app = fixture.debugElement.componentInstance;
    app.factorizedNumbers.splice(0, app.factorizedNumbers.length);

    fixture.detectChanges();
    fixture.whenStable().then(() => {
      const compiled = fixture.debugElement.nativeElement;
      const resultsPanel = compiled.querySelector('#results-panel');
      expect(resultsPanel.innerHTML).not.toContain('Number 10 decompose to factors:');
      expect(resultsPanel.innerHTML).not.toContain('>2, <');
      expect(resultsPanel.innerHTML).not.toContain('>5<');


      app.factorizedNumbers.push({
        numberToFactorize: 10,
        factors: [2, 5]
      });


      fixture.detectChanges();
      fixture.whenStable().then(() => {
        const compiled = fixture.debugElement.nativeElement;
        const resultsPanel = compiled.querySelector('#results-panel');
        expect(resultsPanel.innerHTML).toContain('Number 10 decompose to factors:');
        const factorTags = resultsPanel.querySelectorAll('span span');
        expect(factorTags['0'].innerHTML.trim()).toBe('2,');
        expect(factorTags['1'].innerHTML.trim()).toBe('5');
      });

    });
  }));

});
