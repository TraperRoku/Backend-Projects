import * as React from 'react';
import './WelcomeContent.css'; // Zaimportuj plik CSS

export default class WelcomeContent extends React.Component {
  render() {
    return (
      <div className="welcome-container">
        <div className="jumbotron">
          <div className="container">
            <h1 className="display-4 welcome-title">Welcome to Fitness Tracker!</h1>
            <p className="lead welcome-message">Login to use your exclusive features.</p>
          </div>
        </div>

        <div className="button-container">
          <button
            className="btn btn-primary welcome-button"
            onClick={this.props.login}
          >
            Login
          </button>
          <button
            className="btn btn-secondary welcome-button"
            onClick={this.props.register}
          >
            Register
          </button>
        </div>
      </div>
    );
  }
}
