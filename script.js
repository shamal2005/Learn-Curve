// File: public/script.js
document.querySelector("form")?.addEventListener("submit", function(e) {
  const inputs = document.querySelectorAll("input[required]");
  for (let input of inputs) {
    if (input.value.trim() === "") {
      alert("Please fill in all fields.");
      e.preventDefault();
      return;
    }
  }

  const confirm = document.querySelector("input[name='confirm']");
  const password = document.querySelector("input[name='password']");
  if (confirm && password && confirm.value !== password.value) {
    alert("Passwords do not match.");
    e.preventDefault();
  }
});