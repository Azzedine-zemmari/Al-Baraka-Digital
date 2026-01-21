import { TestBed } from '@angular/core/testing';
import { AppComponent } from './app.component';

describe('AppComponent', () => {

  // This runs before each test
  beforeEach(async () => {

    // Create a fake Angular testing module
    await TestBed.configureTestingModule({

      // Since AppComponent is standalone, we import it
      imports: [AppComponent],

    }).compileComponents();
  });

  // ✅ Test 1: check if the component is created
  it('should create the app', () => {

    // Create the component instance
    const fixture = TestBed.createComponent(AppComponent);

    // Get the component class (TypeScript)
    const app = fixture.componentInstance;

    // Expect the component to exist
    expect(app).toBeTruthy();
  });

  // ✅ Test 2: check the title value in the component class
  it(`should have the 'frontend' title`, () => {

    const fixture = TestBed.createComponent(AppComponent);
    const app = fixture.componentInstance;

    // Check the title property value
    expect(app.title).toBe('frontend');
  });

  // ✅ Test 3: check if the title is rendered in HTML
  it('should render title', () => {

    const fixture = TestBed.createComponent(AppComponent);

    // Trigger Angular change detection (render HTML)
    fixture.detectChanges();

    // Get the rendered HTML
    const compiled = fixture.nativeElement as HTMLElement;

    // Check if HTML contains the title
    expect(compiled.textContent).toContain('frontend');
  });
});
