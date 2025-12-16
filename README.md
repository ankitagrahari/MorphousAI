# MorphousAI

**MorphousAI** is a simple and intuitive AI application that lets you **upload images**, **generate images from text**, and **convert text to audio** — all powered by **OpenAI APIs**.

This project showcases how you can build a multi-modal AI tool using OpenAI capabilities to handle **image generation**, **text-to-image**, and **text-to-speech** workflows.  [GitHub](https://github.com/ankitagrahari/MorphousAI)

---

## 🚀 Features

✔️ Upload an image and generate outputs  
✔️ Convert user-entered text into another image  
✔️ Convert text to natural-sounding audio  
✔️ Uses OpenAI APIs for generative models  
✔️ Easy to extend & customize

---

## 🧠 How It Works

This project combines several AI modalities:

1. **Image Upload Module**  
   - Allows users to upload an image from their device.

2. **Text-to-Image Conversion**  
   - Uses OpenAI’s image generation APIs to create images from prompts.

3. **Text-to-Audio Conversion**  
   - Uses OpenAI’s text-to-speech APIs to synthesize speech from text.

Developers can extend or modify this to include additional AI workflows like speech-to-text, image captioning, or more advanced multi-modal interactions.

---

## 🛠️ Tech Stack

📌 Java (primary codebase)  
📌 Spring AI to integrate with OpenAI (for image & audio generation)  
📌 Vaadin - Web frontend for uploads & interaction  
---

## 🔧 Prerequisites

Before running the project locally, make sure you have:
- Docker Running  
- An **OpenAI API Key** (sign up at https://platform.openai.com/)

---

## 📝 Setup & Installation

1. **Clone the repository**

```bash
git clone https://github.com/ankitagrahari/MorphousAI.git
cd MorphousAI
```
2.	Install dependencies
```bash
mvn clean install
```
3.	Configure your environment
```bash
Create a .env file in the root:

OPENAI_API_KEY=<your_openai_api_key_here>
```
⚠️ Make sure not to commit your .env file or API key to GitHub.

	4.	Start the app
```bash
mvn spring-boot:run -Dspring-boot.run.arguments="--server.port=9090"
```
Your app should now be running locally.

⸻

📌 Usage

✏️ Text to Image

	1.	Enter a text prompt.
	2.	The app sends the prompt to the OpenAI image API.
	3.	View the generated image in the UI.

🔉 Text to Audio

	1.	Enter text you want to hear.
	2.	The app uses OpenAI’s text-to-speech API.
	3.	Download/play the generated audio.

📷 Image Upload

	1.	Upload an image from your device.
	2.	Extend this module to perform image captioning, text extraction, or further generative steps.

⸻

🧪 Example Screenshots

<img width="503" height="226" alt="Screenshot 2025-12-16 at 7 35 34 AM" src="https://github.com/user-attachments/assets/d6276ea9-a99c-433d-95aa-9f4886ab7b91" />

<img width="483" height="389" alt="Screenshot 2025-12-16 at 7 35 59 AM" src="https://github.com/user-attachments/assets/4ec31ae1-1d70-4437-bc4d-581a6d19b5bc" />

<img width="488" height="544" alt="Screenshot 2025-12-16 at 7 37 12 AM" src="https://github.com/user-attachments/assets/dc978c44-b3f7-46c1-8672-e27bcc8fdead" />

<img width="1000" height="605" alt="Screenshot 2025-12-16 at 7 39 36 AM" src="https://github.com/user-attachments/assets/9f765f59-a4aa-440d-8700-32c6a90e0096" />


⸻

🧠 How to Contribute

Contributions are welcome! You can help by:
	•	Adding new AI features (speech-to-text, image captioning)
	•	Improving UI/UX
	•	Writing tests or bug fixes
	•	Updating documentation

To contribute:

git fork https://github.com/ankitagrahari/MorphousAI
git clone <your_forked_repo>
git checkout -b feature/my-awesome-feature

Submit a pull request and describe your changes.

⸻

📄 License

Distributed under the MIT License.
See LICENSE for more information.

⸻

❤️ Acknowledgements

Thanks to:
	•	OpenAI for powerful generative models 🌟
	•	✨ The developer community for inspiration

⸻

📞 Contact

If you have questions, feel free to reach out:

Ankit Agrahari –
💬 GitHub: https://github.com/ankitagrahari
📎 Email: (ankitagrahari.rkgit@gmail.com)

⸻

🎉 Happy building with MorphousAI!
