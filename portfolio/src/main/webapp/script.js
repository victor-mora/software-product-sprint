// Copyright 2019 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     https://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

/**
 * Adds a random greeting to the page.
 */
function addRandomGreeting() {
  const greetings =
  ['This webpage is made with HTML, CSS, and JavaScript!', 
  'Thank you for visiting my page!', 'Hello!', 
  'Outside of my studies, I enjoy weightlifting and reading!'];
      //['Hello world!', '¡Hola Mundo!', '你好，世界！', 'Bonjour le monde!'];

  // Pick a random greeting.
  const greeting = greetings[Math.floor(Math.random() * greetings.length)];

  // Add it to the page.
  const greetingContainer = document.getElementById('greeting-container');
  greetingContainer.innerText = greeting;
}

/**
 * week 1
 * Generates a URL for a random image in the images directory and adds an img
 * element with that URL to the page.
 */
function randomImage() {
  // generate a random index between
  // 1 and 3.
  const imageIndex = Math.floor(Math.random() * 3) + 1;
  const imgUrl = 'images/rando-' + imageIndex + '.jpg';

  const imgElement = document.createElement('img');
  imgElement.src = imgUrl;

  const imageContainer = document.getElementById('random-image-container');
  // Remove the previous image.
  imageContainer.innerHTML = '';
  imageContainer.appendChild(imgElement);
}

/**
 * week 2 step 2
 * Another way to use fetch is by using the async and await keywords. This
 * allows you to use the return values directly instead of going through
 * Promises.
 */
async function getDataUsingAsyncAwait() {
  const response = await fetch('/data');
  const quote = await response.text();
  document.getElementById('data-container').innerText = quote;
}

/**
 * week 2 step 3
 * Fetches comments from the servers and adds them to the DOM.
 */
function getCommentList() {
  fetch('/data').then(response => response.json()).then((comList) => {
    // comList is an object, not a string, so we have to
    // reference its fields to create HTML content

    const comListElement = document.getElementById('data-container');
    comListElement.innerHTML = '';
    comListElement.appendChild(
        createListElement(comList[0]));
    comListElement.appendChild(
        createListElement(comList[1]));
    comListElement.appendChild(
        createListElement(comList[2]));
  });
}

/** Creates an <li> element containing text. */
function createListElement(text) {
  const liElement = document.createElement('li');
  liElement.innerText = text;
  return liElement;
}