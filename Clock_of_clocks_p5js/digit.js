// Loïc Bertrand
// Clock of clocks with p5.js
// Inspired by this video : https://www.youtube.com/watch?v=ExkVIQ60ClM

class Digit {

  constructor(x, y) {
    if (!x && !y) {
      x = 0;
      y = 0;
    }
    this.corner = createVector(x, y);
    this.clocks = [];
    for (let j = 0; j < 6; j++) {
      for (let i = 0; i < 5; i++) {
        this.clocks.push(new Clock(x + i * scl, y + j * scl));
      }
    }
  }

  set(dig, amount) {
    if (!amount) {
      amount = 1;
    }
    const model = positions[dig];
    for (let i = 0; i < model.length; i++) {
      this.clocks[i].set(model[i].hour, model[i].min, amount);
    }
  }

  show() {
    for (let c of this.clocks) {
      c.show();
    }
  }

}
