<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>AbinayaMart AI Chatbot</title>
    <style>
        * {
            box-sizing: border-box;
        }

        body {
            margin: 0;
            font-family: Arial, sans-serif;
            background: #f3f4f6;
            display: flex;
            justify-content: center;
            align-items: center;
            min-height: 100vh;
        }

        .chat-container {
            width: 100%;
            max-width: 430px;
            height: 600px;
            background: white;
            border-radius: 16px;
            box-shadow: 0 4px 20px #0002;
            display: flex;
            flex-direction: column;
            overflow: hidden;
        }

        .chat-header {
            background: #2563eb;
            color: white;
            padding: 20px;
        }

        .chat-header h2 {
            margin: 0 0 6px;
        }

        .chat-header p {
            margin: 0;
            font-size: 13px;
        }

        .messages {
            flex: 1;
            padding: 16px;
            overflow-y: auto;
            display: flex;
            flex-direction: column;
            gap: 12px;
        }

        .message {
            padding: 11px 14px;
            border-radius: 12px;
            max-width: 85%;
            line-height: 1.5;
            overflow-wrap: anywhere;
        }

        .bot {
            background: #e5e7eb;
            align-self: flex-start;
        }

        .user {
            background: #2563eb;
            color: white;
            align-self: flex-end;
        }

        .chat-form {
            display: flex;
            padding: 12px;
            gap: 8px;
            border-top: 1px solid #ddd;
        }

        .chat-form input {
            flex: 1;
            min-width: 0;
            padding: 12px;
            border: 1px solid #ccc;
            border-radius: 8px;
            font-size: 15px;
        }

        .chat-form button {
            border: none;
            border-radius: 8px;
            padding: 12px 16px;
            color: white;
            background: #2563eb;
            cursor: pointer;
        }

        .chat-form button:disabled {
            opacity: 0.6;
            cursor: wait;
        }

        .back-link {
            display: block;
            text-align: center;
            padding: 12px;
            color: #2563eb;
            text-decoration: none;
        }
    </style>
</head>
<body>

<div class="chat-container">
    <div class="chat-header">
        <h2>AbinayaMart Assistant 🤖</h2>
        <p>How can I help you today?</p>
    </div>

    <div class="messages" id="messages" aria-live="polite">
        <div class="message bot">
            Hi! Welcome to AbinayaMart. Ask me about products,
            shopping, orders, or using our website.
        </div>
    </div>

    <form class="chat-form" id="chatForm">
        <input
            type="text"
            id="userMessage"
            placeholder="Type your message..."
            aria-label="Your message"
            required
            maxlength="1000">
        <button type="submit" id="sendButton">Send</button>
    </form>

    <a class="back-link" href="products.jsp">← Back to Products</a>
</div>

<script>
    const form = document.getElementById("chatForm");
    const input = document.getElementById("userMessage");
    const messages = document.getElementById("messages");
    const sendButton = document.getElementById("sendButton");

    function addMessage(text, type) {
        const message = document.createElement("div");
        message.className = "message " + type;
        message.textContent = text;
        messages.appendChild(message);
        messages.scrollTop = messages.scrollHeight;
    }

    form.addEventListener("submit", async function (event) {
        event.preventDefault();

        const text = input.value.trim();
        if (!text) return;

        addMessage(text, "user");
        input.value = "";
        sendButton.disabled = true;
        sendButton.textContent = "Sending...";

        try {
            const response = await fetch(
                "<%= request.getContextPath() %>/chat",
                {
                    method: "POST",
                    headers: {
                        "Content-Type": "application/json"
                    },
                    body: JSON.stringify({ message: text })
                }
            );

            const data = await response.json();

            if (!response.ok) {
                throw new Error(data.reply || "Unable to get a response.");
            }

            addMessage(
                data.reply || "Sorry, I couldn't answer that.",
                "bot"
            );
        } catch (error) {
            addMessage(
                "Chat service is not connected yet. We will finish the Java backend next.",
                "bot"
            );
        } finally {
            sendButton.disabled = false;
            sendButton.textContent = "Send";
            input.focus();
        }
    });
</script>

</body>
</html>