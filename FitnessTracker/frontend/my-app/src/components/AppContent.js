import * as React from 'react';
import Header from './Header';
import WelcomeContent from './WelcomeContent';
import LoginForm from './LoginForm';
import RegisterForm from './RegisterForm';
import logo from '../fitness.png';
import WorkoutApp from './Workout/WorkoutApp';
import { request, setAuthHeader } from '../axios_helper';

export default class AppContent extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            componentToShow: "welcome", // Domyślny widok
            loggedIn: false,           // Czy użytkownik jest zalogowany
            errorMessage: "",          // Wiadomość o błędzie
        };
    }

    // Metoda do obsługi wyświetlania odpowiedniego komponentu
    setComponentToShow = (component) => {
        this.setState({ componentToShow: component });
    };

    login = () => {
        this.setComponentToShow("login");
    };

    register = () => {
        this.setComponentToShow("register");
    };

    logout = () => {
        this.setState({ componentToShow: "welcome", loggedIn: false });
        setAuthHeader(null);
    };

    onLogin = (e, username, password) => {
        e.preventDefault();
        request("POST", "/login", { login: username, password: password })
            .then((response) => {
                this.setState({
                    componentToShow: "messages",
                    loggedIn: true,
                    errorMessage: "",
                });
                setAuthHeader(response.data.token);
            })
            .catch(() => {
                this.setState({
                    errorMessage: "Login failed. Please check your credentials.",
                });
            });
    };

    onRegister = (e, firstName, lastName, username, password) => {
        e.preventDefault();
        request("POST", "/register", {
            firstName: firstName,
            lastName: lastName,
            login: username,
            password: password,
        })
            .then((response) => {
                this.setState({
                    componentToShow: "messages",
                    loggedIn: true,
                    errorMessage: "",
                });
                setAuthHeader(response.data.token);
            })
            .catch(() => {
                this.setState({
                    errorMessage: "Registration failed. Please try again.",
                });
            });
    };

    componentDidMount() {
        this.logout(); // Domyślnie wyloguj użytkownika przy pierwszym montażu
    }

    renderContent() {
        const { componentToShow } = this.state;

        switch (componentToShow) {
            case "welcome":
                return (
                    <WelcomeContent
                        login={this.login}
                        register={this.register}
                    />
                );
            case "login":
                return (
                    <LoginForm
                        onLogin={this.onLogin}
                        onRegister={this.register}
                    />
                );
            case "register":
                return (
                    <RegisterForm
                        onRegister={this.onRegister}
                        onLogin={this.login}
                    />
                );
            case "messages":
                return <WorkoutApp />;
            default:
                return null;
        }
    }

    render() {
        const { errorMessage, loggedIn } = this.state;

        return (
            <div>
                <Header
                    pageTitle="Fitness-Tracker"
                    logoSrc={logo}
                    login={this.login}
                    logout={this.logout}
                    loggedIn={loggedIn}
                />

                {this.renderContent()}

                {errorMessage && (
                    <div className="error-message">
                        <p>{errorMessage}</p>
                    </div>
                )}
            </div>
        );
    }
}
